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

    /**
     * 주문 상세 정보를 저장합니다.
     *
     * @param orderId 주문 번호
     * @param requestDtoList 주문 상세 정보 목록
     */
    void createBookOrderDetail(Long orderId, List<BookOrderDetailCreateRequestDto> requestDtoList);

    /**
     * 사용한 쿠폰 번호 목록을 조회합니다.
     *
     * @param orderCode 주문 코드
     * @return 사용한 쿠폰 번호 목록
     */
    List<Long> getUsedCouponIdByOrderCode(String orderCode);
}