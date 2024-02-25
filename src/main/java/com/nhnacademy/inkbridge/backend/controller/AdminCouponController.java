package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AdminCouponController.
 *
 * @author JBum
 * @version 2024/02/22
 */
@RestController
@RequestMapping("/api/admin/coupons")
public class AdminCouponController {

    private final CouponService couponService;

    public AdminCouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<Page<CouponReadResponseDto>> getCoupons(
        @RequestParam(name = "coupon-status-id") int couponStatusId, Pageable pageable) {
        Page<CouponReadResponseDto> coupons = couponService.adminViewCoupons(pageable,
            couponStatusId);
        return ResponseEntity.ok(coupons);
    }
}
