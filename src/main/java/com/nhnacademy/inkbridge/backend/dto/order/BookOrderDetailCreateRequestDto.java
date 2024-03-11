package com.nhnacademy.inkbridge.backend.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookOrderDetailCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@NoArgsConstructor
@Getter
public class BookOrderDetailCreateRequestDto {

    private Long bookId;
    private Long price;
    private Integer amount;
    private Long wrappingId;
    private String couponId;
    private Long wrappingPrice;
}