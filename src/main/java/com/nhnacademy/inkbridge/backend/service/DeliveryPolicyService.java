package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import java.util.List;

/**
 * class: DeliveryPolicyService.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
public interface DeliveryPolicyService {

    List<DeliveryPolicyReadResponseDto> getDeliveryPolicies();

    DeliveryPolicyReadResponseDto getDeliveryPolicy(Long deliveryPolicyId);

    DeliveryPolicyReadResponseDto getCurrentDeliveryPolicy();

    void createDeliveryPolicy(DeliveryPolicyCreateRequestDto deliveryPolicyCreateRequestDto);
}
