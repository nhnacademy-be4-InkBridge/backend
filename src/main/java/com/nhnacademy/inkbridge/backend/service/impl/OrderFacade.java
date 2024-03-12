package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
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
    public String createOrder(OrderCreateRequestDto requestDto) {
        // 주문 정보 저장 후 주문 번호 받아오기
        String orderId = bookOrderService.createBookOrder(requestDto.getBookOrder());
        // 주문 상세 저장
        bookOrderDetailService.createBookOrderDetail(orderId, requestDto.getBookOrderList());
        //

        return orderId;
    }

    @Transactional(readOnly = true)
    public OrderPayInfoReadResponseDto getOrderPaymentInfo(String orderId) {
        return bookOrderService.getOrderPaymentInfoByOderId(orderId);
    }
}
