package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.IssueCouponRequestDto;

/**
 * class: CouponService.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponService {

    void createCoupon(CouponCreateRequestDto couponCreateRequestDTO);

    void issueCoupon(IssueCouponRequestDto issueCouponDto);
}
