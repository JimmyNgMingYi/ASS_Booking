package com.nusiss.ass.booking.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    private String bookingId;

    private int userId;
    private int productId;

    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private double totalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public LocalDate getBookingStartDate() { return bookingStartDate; }
    public void setBookingStartDate(LocalDate bookingStartDate) { this.bookingStartDate = bookingStartDate; }

    public LocalDate getBookingEndDate() { return bookingEndDate; }
    public void setBookingEndDate(LocalDate bookingEndDate) { this.bookingEndDate = bookingEndDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
