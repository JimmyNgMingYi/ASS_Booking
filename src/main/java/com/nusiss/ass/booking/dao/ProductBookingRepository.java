package com.nusiss.ass.booking.dao;

import com.nusiss.ass.booking.model.ProductBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBookingRepository extends JpaRepository<ProductBooking, Long> {}
