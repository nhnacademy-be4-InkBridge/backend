package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import java.util.List;

/**
 * class: BookOrderDetailRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
public interface BookOrderDetailRepositoryCustom {

    /**
     * 주문 상세 조회 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 상세 목록
     */
    List<OrderDetailReadResponseDto> findAllByOrderId(Long orderId);

    /**
     * 주문 상세 조회 메소드입니다.
     *
     * @param orderCode 주문 코드
     * @return 상세 목록
     */
    List<OrderDetailReadResponseDto> findAllByOrderCode(String orderCode);

    /**
     * 주문 상세 목록을 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 상세 목록
     */
    List<BookOrderDetail> findOrderDetailByOrderId(Long orderId);
}
