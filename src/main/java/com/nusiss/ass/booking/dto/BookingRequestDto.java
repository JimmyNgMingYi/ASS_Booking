package com.nusiss.ass.booking.dto;

import java.time.LocalDate;

public class BookingRequestDto {
    private int userId;
    private int productId;
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
	private double totalAmount;

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public LocalDate getBookingStartDate() { return bookingStartDate; }
    public void setBookingStartDate(LocalDate bookingStartDate) { this.bookingStartDate = bookingStartDate; }

    public LocalDate getBookingEndDate() { return bookingEndDate; }
    public void setBookingEndDate(LocalDate bookingEndDate) { this.bookingEndDate = bookingEndDate; }
	
	// ✅ Add getter and setter for totalAmount
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
