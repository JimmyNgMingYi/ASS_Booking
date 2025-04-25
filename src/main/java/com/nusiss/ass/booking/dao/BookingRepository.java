package com.nusiss.ass.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nusiss.ass.booking.model.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query("SELECT b FROM Booking b WHERE b.bookingId = :bookingId")
    Optional<Booking> findByBookingId(@Param("bookingId") String bookingId);

    // ✅ Check if there's any overlapping booking for a product
    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
           "WHERE b.productId = :productId " +
           "AND b.bookingStartDate < :endDate " +
           "AND b.bookingEndDate > :startDate")
    boolean existsOverlappingBooking(
        @Param("productId") int productId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
