package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: CouponMessageEnum.
 *
 * @author minm063
 * @version 2024/02/15
 */
public enum CouponMessageEnum {
    COUPON_VALIDATION_ERROR("쿠폰 생성 규칙이 맞지 않습니다.");

    private final String message;

    CouponMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
