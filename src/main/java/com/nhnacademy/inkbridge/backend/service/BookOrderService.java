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

    /**
     * 주문을 생성하는 메소드입니다.
     *
     * @param requestDto 주문 정보
     * @return 주문 번호
     */
    String createBookOrder(BookOrderCreateRequestDto requestDto);

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 결제 정보
     */
    OrderPayInfoReadResponseDto getOrderPaymentInfoByOderId(String orderId);
}
