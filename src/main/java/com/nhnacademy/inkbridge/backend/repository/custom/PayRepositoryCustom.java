package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import java.util.Optional;

/**
 * class: PayRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
public interface PayRepositoryCustom {

    Optional<PayReadResponseDto> findPayByPayId(Long payId);

    Optional<PayReadResponseDto> findPayByOrderId(Long orderId);

}
