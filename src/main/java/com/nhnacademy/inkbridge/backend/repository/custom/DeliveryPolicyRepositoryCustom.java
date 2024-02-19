package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: DeliveryPolicyRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@NoRepositoryBean
public interface DeliveryPolicyRepositoryCustom {

    List<DeliveryPolicyReadResponseDto> findAllDeliveryPolicyBy();

    DeliveryPolicyReadResponseDto findDeliveryPolicyById(Long deliveryPolicyId);

    DeliveryPolicyReadResponseDto findCurrentPolicy();
}
