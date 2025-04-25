package com.nusiss.ass.booking.controller;

import com.nusiss.ass.booking.dto.BookingRequestDto;
import com.nusiss.ass.booking.dto.BookingResponseDto;
import com.nusiss.ass.booking.dto.BookingDetailsDto;
import com.nusiss.ass.booking.dto.BookingBaseResponse;
import com.nusiss.ass.booking.model.Booking;
import com.nusiss.ass.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

   @PostMapping
public ResponseEntity<BookingBaseResponse> createBooking(@RequestBody BookingRequestDto request) {
    return bookingService.createBooking(request);
}


    @GetMapping("/{bookingId}")
public ResponseEntity<BookingDetailsDto> getBooking(@PathVariable String bookingId) {
    Booking booking = bookingService.getBookingById(bookingId);
    if (booking == null) {
        return ResponseEntity.notFound().build();
    }

    BookingDetailsDto response = new BookingDetailsDto();
    response.setBookingId(booking.getBookingId());
    response.setUserId(booking.getUserId());
    response.setProductId(booking.getProductId());
    response.setBookingStartDate(booking.getBookingStartDate());
    response.setBookingEndDate(booking.getBookingEndDate());
    response.setStatus(booking.getStatus().toString());
    response.setTotalAmount(booking.getTotalAmount());
    response.setCreatedAt(booking.getCreatedAt());

    return ResponseEntity.ok(response);
}

  // ✅ New endpoint to check availability
  @GetMapping("/availability")
public ResponseEntity<?> checkAvailability(
        @RequestParam int productId,
        @RequestParam String startDate,
        @RequestParam String endDate
) {
    try {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // ✅ Validate date logic
        if (!end.isAfter(start)) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "available", false,
                    "message", "End date must be after start date."
                )
            );
        }

        boolean available = bookingService.isAvailable(productId, start, end);
        return ResponseEntity.ok(
            Map.of(
                "available", available,
                "message", available 
                    ? "Product is available for the selected dates." 
                    : "Product is NOT available for the selected dates."
            )
        );
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(
            Map.of(
                "available", false,
                "message", "Missing or invalid parameters."
            )
        );
    }
}


    @GetMapping(value = {"", "/"})
public String home() {
    return "✅ Booking Microservice is up and running!";
}

}
