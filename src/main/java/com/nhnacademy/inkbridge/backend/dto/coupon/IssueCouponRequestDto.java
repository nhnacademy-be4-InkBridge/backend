package com.nhnacademy.inkbridge.backend.dto.coupon;

import lombok.Getter;

/**
 * class: IssueCouponDto.
 *
 * @author JBum
 * @version 2024/02/19
 */
@Getter
public class IssueCouponRequestDto {
    Long memberId;
    String couponId;
}
