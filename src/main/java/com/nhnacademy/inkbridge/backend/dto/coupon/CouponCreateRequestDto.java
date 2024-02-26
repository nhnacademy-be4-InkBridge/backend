package com.nhnacademy.inkbridge.backend.dto.coupon;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CouponCreateRequestDTO.
 *
 * @author JBum
 * @version 2024/02/15
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @FutureOrPresent
    private LocalDate basicIssuedDate;
    @NotNull
    @FutureOrPresent
    private LocalDate basicExpiredDate;
    @NotNull
    @Min(value = 0)
    private Integer validity;
    @NotNull
    private Integer couponTypeId;
    @NotNull
    private Boolean isBirth;

    private Set<Long> categoryIds;
    private Set<Long> bookIds;
}