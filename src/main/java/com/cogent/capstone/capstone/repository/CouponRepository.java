package com.cogent.capstone.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogent.capstone.capstone.entities.Coupon;
import com.cogent.capstone.capstone.entities.Order;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value = "SELECT * FROM coupon where code = ?", nativeQuery = true)
    Coupon findByCode(String code);
}
