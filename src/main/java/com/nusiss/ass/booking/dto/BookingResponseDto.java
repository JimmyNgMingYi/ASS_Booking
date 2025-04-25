package com.nusiss.ass.booking.dto;

import lombok.Data;

@Data
public class BookingResponseDto implements BookingBaseResponse {
    private String bookingId;
    private double totalAmount;
    private String status;
}
