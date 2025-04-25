package com.nusiss.ass.booking.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingDetailsDto {
    private String bookingId;
    private int userId;
    private int productId;
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
    private String status;
    private double totalAmount;
    private LocalDateTime createdAt;
}
