package com.nhnacademy.inkbridge.backend.dto.coupon;

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
    private Integer couponTypeId;
    private String couponTypeName;
    private Boolean isBirth;
    private Integer couponStatusId;
    private String couponStatusName;

    @Builder
    public MemberCouponReadResponseDto(String memberCouponId, LocalDate expiredAt, LocalDate usedAt,
        String couponName, Long minPrice, Long discountPrice, Long maxDiscountPrice,
        Integer couponTypeId, String couponTypeName, Boolean isBirth, Integer couponStatusId,
        String couponStatusName) {
        this.memberCouponId = memberCouponId;
        this.expiredAt = expiredAt;
        this.usedAt = usedAt;
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.couponTypeId = couponTypeId;
        this.couponTypeName = couponTypeName;
        this.isBirth = isBirth;
        this.couponStatusId = couponStatusId;
        this.couponStatusName = couponStatusName;
    }
}
