package com.nhnacademy.inkbridge.backend.dto.order;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookOrderDetailResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@Builder
@AllArgsConstructor
@Getter
public class BookOrderDetailResponseDto {

    private OrderResponseDto orderInfo;
    private List<OrderDetailReadResponseDto> orderDetailInfoList;
}
