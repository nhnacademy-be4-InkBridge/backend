package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: OrderServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderFacade {

    private final BookOrderService bookOrderService;
    private final BookOrderDetailService bookOrderDetailService;


    /**
     * 주문 , 주문 상세 테이블에 정보를 저장합니다.
     *
     * @param requestDto 주문 정보
     * @return 주문 번호
     */
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {
        OrderCreateResponseDto responseDto = bookOrderService.createBookOrder(requestDto.getBookOrder());
        bookOrderDetailService.createBookOrderDetail(responseDto.getOrderId(), requestDto.getBookOrderList());

        return responseDto;
    }

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 결제 정보
     */
    @Transactional(readOnly = true)
    public OrderPayInfoReadResponseDto getOrderPaymentInfo(String orderId) {
        return bookOrderService.getOrderPaymentInfoByOderId(orderId);
    }
}
