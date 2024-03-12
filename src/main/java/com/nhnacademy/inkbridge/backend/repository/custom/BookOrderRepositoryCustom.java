package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import java.util.Optional;

/**
 * class: BookOrderRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/03/12
 */
public interface BookOrderRepositoryCustom {

    /**
     * 주문 결제 정보를 조회하는 메소드입니다.
     *
     * @param orderId 주문 번호
     * @return 주문 결제 정보
     */
    Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderId(String orderId);
}
