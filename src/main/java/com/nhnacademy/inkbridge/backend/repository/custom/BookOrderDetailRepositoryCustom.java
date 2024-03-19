package com.nhnacademy.inkbridge.backend.repository.custom;

import java.util.List;

/**
 * class: BookOrderDetailRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/03/19
 */
public interface BookOrderDetailRepositoryCustom {

    /**
     * 주문에 사용한 쿠폰 번호 목록을 조회합니다.
     *
     * @param orderCode 주문 코드
     * @return 사용한 쿠폰 번호 목록
     */
    List<Long> findAllByOrderCode(String orderCode);
}
