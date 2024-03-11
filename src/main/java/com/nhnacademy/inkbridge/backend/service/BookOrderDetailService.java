package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import java.util.List;

/**
 * class: BookOrderDetailService.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderDetailService {

    void createBookOrderDetail(String orderId, List<BookOrderDetailCreateRequestDto> requestDtoList);
}
