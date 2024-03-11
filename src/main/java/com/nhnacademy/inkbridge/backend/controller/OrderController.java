package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class OrderController {

    private final OrderService orderService;

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
            .body(orderService.createOrder(orderCreateRequestDto));
    }
}
