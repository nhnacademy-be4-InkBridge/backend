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

    List<OrderDetailReadResponseDto> findAllByMemberIdAndOrderId(Long orderId);

    List<OrderDetailReadResponseDto> findAllByMemberIdAndOrderCode(String orderCode);

    /**
     * 주문에 사용한 쿠폰 번호 목록을 조회합니다.
     *
     * @param orderId 주문 코드
     * @return 사용한 쿠폰 번호 목록
     */
    List<BookOrderDetail> findAllByOrderId(Long orderId);

    /**
     * 주문에 사용한 쿠폰 번호 목록을 조회합니다.
     *
     * @param orderCode 주문 코드
     * @return 사용한 쿠폰 번호 목록
     */
    List<Long> findAllByOrderCode(String orderCode);
}
