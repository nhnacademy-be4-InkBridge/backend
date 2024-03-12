package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.impl.OrderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: OrderController.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderFacade orderFacade;

    /**
     * 주문 등록 요청을 처리하는 메소드입니다.
     *
     * @param orderCreateRequestDto 주문 정보
     * @return 주문 번호
     */
    @PostMapping
    public ResponseEntity<String> createOrder(
        @RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(orderFacade.createOrder(orderCreateRequestDto));
    }

    @GetMapping("/{orderId}/order-pays")
    public ResponseEntity<OrderPayInfoReadResponseDto> getOrderPaymentInfo(
        @PathVariable String orderId) {
        OrderPayInfoReadResponseDto orderPaymentInfo = orderFacade.getOrderPaymentInfo(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderPaymentInfo);
    }
}
