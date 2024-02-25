package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponIssueRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: CouponService.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponService {

    void createCoupon(CouponCreateRequestDto couponCreateRequestDTO);

    void issueCoupon(CouponIssueRequestDto issueCouponDto);

    Page<CouponReadResponseDto> adminViewCoupons(Pageable pageable, int couponType);
}
