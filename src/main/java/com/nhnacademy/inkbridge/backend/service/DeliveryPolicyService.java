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

    /**
     * 배송비 정책을 전체 조회하는 메소드입니다.
     *
     * @return List - DeliveryPolicyReadResponseDto
     */
    List<DeliveryPolicyReadResponseDto> getDeliveryPolicies();

    /**
     * 배송비 정책 id로 배송비 정책을 조회하는 메소드입니다.
     *
     * @param deliveryPolicyId Long
     * @return DeliveryPolicyReadResponseDto
     */
    DeliveryPolicyReadResponseDto getDeliveryPolicy(Long deliveryPolicyId);

    /**
     * 현재 사용중인 배송비 정책을 조회하는 메소드입니다.
     *
     * @return DeliveryPolicyReadResponseDto
     */
    DeliveryPolicyReadResponseDto getCurrentDeliveryPolicy();

    /**
     * 배송비 정책을 생성하는 메소드입니다.
     *
     * @param deliveryPolicyCreateRequestDto DeliveryPolicyCreateRequestDto
     */
    void createDeliveryPolicy(DeliveryPolicyCreateRequestDto deliveryPolicyCreateRequestDto);
}
