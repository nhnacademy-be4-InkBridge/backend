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
    Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderId(String orderId);
}
