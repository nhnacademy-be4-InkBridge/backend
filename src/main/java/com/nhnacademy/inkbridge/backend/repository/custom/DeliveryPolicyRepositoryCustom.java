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

    /**
     * 배송비 정책 전체 리스트를 조회하는 메소드입니다.
     *
     * @return List - DeliveryPolicyReadResponseDto
     */
    List<DeliveryPolicyReadResponseDto> findAllDeliveryPolicyBy();

    /**
     * 배송비 정책 id로 배송비 정책을 조회하는 메소드입니다.
     *
     * @param deliveryPolicyId Long
     * @return DeliveryPolicyReadResponseDto
     */
    DeliveryPolicyReadResponseDto findDeliveryPolicyById(Long deliveryPolicyId);

    /**
     * 현재 적용중인 배송비 정책을 조회하는 메소드입니다.
     *
     * @return DeliveryPolicyReadResponseDto
     */
    DeliveryPolicyReadResponseDto findCurrentPolicy();
}
