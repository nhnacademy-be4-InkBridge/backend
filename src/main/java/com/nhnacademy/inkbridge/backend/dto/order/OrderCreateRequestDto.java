package com.nhnacademy.inkbridge.backend.dto.order;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: OrderCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private BookOrderCreateRequestDto bookOrderDetailCreateRequestDto;
    private List<BookOrderDetailCreateRequestDto> bookOrderList;


}
