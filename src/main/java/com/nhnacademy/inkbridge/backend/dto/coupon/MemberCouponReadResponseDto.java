package com.nhnacademy.inkbridge.backend.dto.coupon;

import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberCouponReadResponseDto.
 *
 * @author JBum
 * @version 2024/03/07
 */
@Getter
@NoArgsConstructor
public class MemberCouponReadResponseDto {

    private String memberCouponId;
    private LocalDate expiredAt;
    private LocalDate usedAt;
    private String couponName;
    private Long minPrice;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private CouponType couponType;
    private Boolean isBirth;
    private CouponStatus couponStatus;

    @Builder
    public MemberCouponReadResponseDto(String memberCouponId, LocalDate expiredAt, LocalDate usedAt,
        String couponName, Long minPrice, Long discountPrice, Long maxDiscountPrice,
        CouponType couponType, Boolean isBirth, CouponStatus couponStatus) {
        this.memberCouponId = memberCouponId;
        this.expiredAt = expiredAt;
        this.usedAt = usedAt;
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.couponType = couponType;
        this.isBirth = isBirth;
        this.couponStatus = couponStatus;
    }
}
