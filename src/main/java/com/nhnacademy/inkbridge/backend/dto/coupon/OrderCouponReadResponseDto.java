package com.nhnacademy.inkbridge.backend.dto.coupon;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: OrderCouponReadResponseDto.
 *
 * @author JBum
 * @version 2024/03/07
 */
@Getter
@NoArgsConstructor
public class OrderCouponReadResponseDto {

    private Long bookId;
    private List<MemberCouponReadResponseDto> memberCouponReadResponseDtos;

    @Builder
    public OrderCouponReadResponseDto(Long bookId,
        List<MemberCouponReadResponseDto> memberCouponReadResponseDtos) {
        this.bookId = bookId;
        this.memberCouponReadResponseDtos = memberCouponReadResponseDtos;
    }
}
