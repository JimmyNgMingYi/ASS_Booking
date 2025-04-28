package com.nusiss.ass.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import com.nusiss.ass.booking.dto.BookingRequestDto;
import com.nusiss.ass.booking.dto.BookingResponseDto;
import com.nusiss.ass.booking.dto.BookingSummaryDto;
import com.nusiss.ass.booking.dto.BookingErrorResponseDto;
import com.nusiss.ass.booking.dto.BookingBaseResponse;
import com.nusiss.ass.booking.model.Booking;
import com.nusiss.ass.booking.model.ProductBooking;
import com.nusiss.ass.booking.model.Status;
import com.nusiss.ass.booking.dao.BookingRepository;
import com.nusiss.ass.booking.dao.ProductBookingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ProductBookingRepository productBookingRepository;

    private static final String SECRET_KEY = "gVuoc5zK4F9Ukr8aWNkohE5ppUZOy2XEjPIGswL0EZo=";

    @Transactional
    public ResponseEntity<BookingBaseResponse> createBooking(BookingRequestDto request) {
        BookingResponseDto response = new BookingResponseDto();
        try {
            // Validate booking dates
            if (request.getBookingEndDate().isBefore(request.getBookingStartDate()) ||
                request.getBookingEndDate().equals(request.getBookingStartDate())) {
                return ResponseEntity
                        .badRequest()
                        .body(new BookingErrorResponseDto(
                                "Invalid Date Range",
                                "End date must be after start date"
                        ));
            }

            // Check availability
            boolean taken = bookingRepository.existsOverlappingBooking(
    request.getProductId(),
    request.getBookingStartDate(),
    request.getBookingEndDate()
);

            if (taken) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new BookingErrorResponseDto(
                                "Already Booked",
                                "The product is not available for the selected dates"
                        ));
            }

            long count = bookingRepository.countTotalBookings();
	    long nextNumber = count + 1;
	    String bookingId = String.format("BK-%05d", nextNumber);
            LocalDateTime createdTime = LocalDateTime.now();

            Booking booking = new Booking();
            booking.setBookingId(encrypt(bookingId));
            booking.setUserId(request.getUserId());
            booking.setProductId(request.getProductId());
            booking.setBookingStartDate(request.getBookingStartDate());
            booking.setBookingEndDate(request.getBookingEndDate());

			booking.setTotalAmount(request.getTotalAmount());

            booking.setStatus(Status.CONFIRMED);
            booking.setCreatedAt(createdTime);
            booking.setUpdatedAt(createdTime);

            booking = bookingRepository.save(booking);

            // ✅ Insert booked dates into product_bookings
            LocalDate date = request.getBookingStartDate();
            while (!date.isAfter(request.getBookingEndDate().minusDays(1))) {
                productBookingRepository.save(new ProductBooking(request.getProductId(), date.atStartOfDay()) );
                date = date.plusDays(1);
            }

            // Prepare response
            response.setBookingId(bookingId);
            response.setStatus(booking.getStatus().toString());
            response.setTotalAmount(booking.getTotalAmount());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public Booking getBookingById(String bookingId) {
        Optional<Booking> optionalBooking = null;
        try {
            optionalBooking = bookingRepository.findByBookingId(encrypt(bookingId));
            if (optionalBooking.isPresent()) {
                Booking booking = optionalBooking.get();
                booking.setBookingId(decrypt(booking.getBookingId()));
                return booking;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return optionalBooking.orElse(null);
    }

   public boolean isAvailable(int productId, LocalDate startDate, LocalDate endDate) {
    return !bookingRepository.existsOverlappingBooking(productId, startDate, endDate);
}

// ✅ New method to list bookings by userId
public List<BookingSummaryDto> getBookingsByUserId(int userId) {
    List<Booking> bookings = bookingRepository.findAllByUserId(userId);
    return bookings.stream().map(booking -> {
        BookingSummaryDto dto = new BookingSummaryDto();
        dto.setBookingId(decryptSafe(booking.getBookingId())); // safe decrypt
        dto.setProductId(booking.getProductId());
        dto.setBookingStartDate(booking.getBookingStartDate());
        dto.setBookingEndDate(booking.getBookingEndDate());
        dto.setStatus(booking.getStatus().toString());
        dto.setTotalAmount(booking.getTotalAmount());
	dto.setCreatedAt(booking.getCreatedAt());
        return dto;
    }).collect(Collectors.toList());
}

// ✅ Helper to decrypt safely without crashing
private String decryptSafe(String bookingId) {
    try {
        return decrypt(bookingId);
    } catch (Exception e) {
        e.printStackTrace();
        return "UNKNOWN";
    }
}


    public static String encrypt(String data) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}
