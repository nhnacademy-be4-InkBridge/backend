package com.nhnacademy.inkbridge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * class: OrderPayInfoReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/12
 */
@Getter
@AllArgsConstructor
@ToString
public class OrderPayInfoReadResponseDto {

    private String orderId;
    private String orderName;
    private Long amount;
}