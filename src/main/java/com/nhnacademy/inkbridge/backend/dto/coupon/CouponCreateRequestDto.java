package com.nhnacademy.inkbridge.backend.dto.coupon;

import java.math.BigInteger;
import java.time.LocalDate;
import lombok.Getter;

/**
 * class: CouponCreateRequestDTO.
 *
 * @author ijeongbeom
 * @version 2024/02/15
 */
@Getter
public class CouponCreateRequestDto {

    private String couponName;
    private Long minPrice;
    private Long maxDiscountPrice;
    private Long discountPrice;
    private LocalDate basicIssuedDate;
    private LocalDate basicExpiredDate;
    private Integer validity;
    private Integer couponTypeId;
    private Boolean isBirth;
}
