package com.cogent.capstone.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogent.capstone.capstone.entities.Coupon;
import com.cogent.capstone.capstone.entities.Product;
import com.cogent.capstone.capstone.repository.CouponRepository;
import com.cogent.capstone.capstone.repository.ProductRepository;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon getCoupon(long id) {

        Optional<Coupon> coupon = couponRepository.findById(id);

        if (coupon.isPresent()) {
            return coupon.get();
        }

        else {
            return null;
        }

    }

    public Coupon getCoupon(String code) {

        return couponRepository.findByCode(code);

    }

    public void deleteCoupon(long id) {

        couponRepository.deleteById(id);

    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

}
