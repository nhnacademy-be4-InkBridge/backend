package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.DeliveryPolicy;
import com.nhnacademy.inkbridge.backend.enums.DeliveryPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.DeliveryPolicyRepository;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: DeliveryServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@Service
@RequiredArgsConstructor
public class DeliveryPolicyServiceImpl implements DeliveryPolicyService {

    private final DeliveryPolicyRepository deliveryPolicyRepository;

    /**
     * 배송비 정책을 전체 조회하는 메소드입니다.
     *
     * @return List - DeliveryPolicyReadResponseDto
     */
    @Transactional(readOnly = true)
    @Override
    public List<DeliveryPolicyReadResponseDto> getDeliveryPolicies() {
        return deliveryPolicyRepository.findAllDeliveryPolicyBy();
    }

    /**
     * 배송비 정책 id로 배송비 정책을 조회하는 메소드입니다.
     *
     * @param deliveryPolicyId Long
     * @return DeliveryPolicyReadResponseDto
     * @throws NotFoundException 배송비 정책 조회 결과가 없는 경우
     */
    @Transactional(readOnly = true)
    @Override
    public DeliveryPolicyReadResponseDto getDeliveryPolicy(Long deliveryPolicyId) {
        if (!deliveryPolicyRepository.existsById(deliveryPolicyId)) {
            throw new NotFoundException(DeliveryPolicyMessageEnum.DELIVERY_POLICY_NOT_FOUND.name());
        }

        return deliveryPolicyRepository.findDeliveryPolicyById(deliveryPolicyId);
    }

    /**
     * 현재 사용중인 배송비 정책을 조회하는 메소드입니다.
     *
     * @return DeliveryPolicyReadResponseDto
     */
    @Transactional(readOnly = true)
    @Override
    public DeliveryPolicyReadResponseDto getCurrentDeliveryPolicy() {
        return deliveryPolicyRepository.findCurrentPolicy();
    }

    /**
     * 배송비 정책을 생성하는 메소드입니다.
     *
     * @param deliveryPolicyCreateRequestDto DeliveryPolicyCreateRequestDto
     */
    @Transactional
    @Override
    public void createDeliveryPolicy(
        DeliveryPolicyCreateRequestDto deliveryPolicyCreateRequestDto) {

        DeliveryPolicy deliveryPolicy = DeliveryPolicy.builder()
            .deliveryPrice(deliveryPolicyCreateRequestDto.getDeliveryPrice())
            .createdAt(LocalDate.now())
            .build();

        deliveryPolicyRepository.save(deliveryPolicy);
    }
}
