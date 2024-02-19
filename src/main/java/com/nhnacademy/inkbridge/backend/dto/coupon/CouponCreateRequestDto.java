package com.nhnacademy.inkbridge.backend.dto.coupon;

import java.math.BigInteger;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * class: CouponCreateRequestDTO.
 *
 * @author JBum
 * @version 2024/02/15
 */
@Getter
public class CouponCreateRequestDto {
    @NotNull
    private String couponName;
    @Column(nullable = false, columnDefinition = "long default 0")
    @NotNull
    private Long minPrice;
    private Long maxDiscountPrice;
    @NotNull
    private Long discountPrice;
    @NotNull
    private LocalDate basicIssuedDate;
    @NotNull
    private LocalDate basicExpiredDate;
    @NotNull
    private Integer validity;
    @NotNull
    private Integer couponTypeId;
    @NotNull
    private Boolean isBirth;
}
