package com.cogent.capstone.capstone.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.capstone.capstone.entities.Coupon;
import com.cogent.capstone.capstone.entities.Product;
import com.cogent.capstone.capstone.services.CouponService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/coupon")
@CrossOrigin
public class CouponController {

    @Autowired
    CouponService couponService;

    @DeleteMapping("deleteCoupon/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        // TODO: process PUT request

        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getCoupon/{id}")
    public ResponseEntity<Coupon> getMethodName(@PathVariable long id) {
        Coupon res = couponService.getCoupon(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("getCouponByCode/{code}")
    public ResponseEntity<Coupon> getMethodName(@PathVariable String code) {
        Coupon res = couponService.getCoupon(code);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("getAllCoupon")
    public ResponseEntity<List<Coupon>> getAll() {
        List<Coupon> res = couponService.getAllCoupons();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("createCoupon")
    public ResponseEntity<String> postMethodName(@RequestBody Coupon entity) {
        // TODO: process POST request

        couponService.createCoupon(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
