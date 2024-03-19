package com.nhnacademy.inkbridge.backend.service;


import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;

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
    OrderCreateResponseDto createBookOrder(BookOrderCreateRequestDto requestDto);

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderCode 주문 번호
     * @return 주문 결제 정보
     */
    OrderPayInfoReadResponseDto getOrderPaymentInfoByOrderCode(String orderCode);

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 코드
     * @return 주문 결제 정보
     */
    OrderPayInfoReadResponseDto getOrderPaymentInfoByOrderId(Long orderId);

    /**
     * 주문의 결제 상태를 변경하는 메소드입니다.
     *
     * @param orderCode 주문 코드
     */
    void updateBookOrderPayStatusByOrderCode(String orderCode);

    /**
     * 결제한 회원 정보와 사용한 포인트 정보를 조회하는 메소드입니다.
     * @param orderCode 주문 번호
     * @return 회원 번호, 사용 포인트
     */
    OrderedMemberPointReadResponseDto getOrderedPersonByOrderCode(String orderCode);

}
