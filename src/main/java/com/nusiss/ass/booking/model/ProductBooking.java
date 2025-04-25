package com.nusiss.ass.booking.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "product_bookings")
public class ProductBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int productId;

    private LocalDate date;

    // Constructors
    public ProductBooking() {}

    public ProductBooking(int productId, LocalDate date) {
        this.productId = productId;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
