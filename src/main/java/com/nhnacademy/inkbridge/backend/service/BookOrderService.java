package com.nhnacademy.inkbridge.backend.service;


import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;

/**
 * class: BookOrderService.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderService {

    String createBookOrder(BookOrderCreateRequestDto requestDto);

    OrderPayInfoReadResponseDto getOrderPaymentInfoByOderId(String orderId);
}
