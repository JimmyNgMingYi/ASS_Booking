package com.nusiss.ass.booking.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "product_bookings")
public class ProductBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int productId;

    private OffsetDateTime date;

    public ProductBooking() {}

    public ProductBooking(int productId, OffsetDateTime date) {
        this.productId = productId;
        this.date = date;
    }

    public Long getId() { return id; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public OffsetDateTime getDate() { return date; }
    public void setDate(OffsetDateTime date) { this.date = date; }
}
