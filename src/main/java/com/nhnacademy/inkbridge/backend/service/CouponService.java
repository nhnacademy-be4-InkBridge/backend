package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.repository.CouponRepository;

/**
 * class: CouponService.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponService {
    void createCoupon(CouponCreateRequestDto couponCreateRequestDTO);
}
