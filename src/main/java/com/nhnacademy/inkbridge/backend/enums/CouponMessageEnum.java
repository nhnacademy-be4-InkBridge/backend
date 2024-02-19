package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: CouponMessageEnum.
 *
 * @author JBum
 * @version 2024/02/15
 */
public enum CouponMessageEnum {
    COUPON_NOT_FOUND("존재하지 않는 쿠폰입니다."),
    COUPON_ID("CouponId: "),
    COUPON_VALIDATION_ERROR("쿠폰 생성 규칙이 맞지 않습니다."),
    COUPON_TYPE_NOT_FOUND("CouponType을 찾을 수 없습니다."),
    COUPON_TYPE_ID("CouponTypeId: "),
    COUPON_ISSUED_EXIST("해당 유저는 이미 발급받은 쿠폰입니다");
    private final String message;

    CouponMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
