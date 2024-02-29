package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.DeliveryPolicy;
import com.nhnacademy.inkbridge.backend.entity.QDeliveryPolicy;
import com.nhnacademy.inkbridge.backend.repository.custom.DeliveryPolicyRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: DeliveryPolicyRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
public class DeliveryPolicyRepositoryImpl extends QuerydslRepositorySupport implements
    DeliveryPolicyRepositoryCustom {

    public DeliveryPolicyRepositoryImpl() {
        super(DeliveryPolicy.class);
    }

    /**
     * {@inheritDoc}
     *
     * @return List - DeliveryPolicyReadResponseDto
     */
    @Override
    public List<DeliveryPolicyReadResponseDto> findAllDeliveryPolicyBy() {
        QDeliveryPolicy deliveryPolicy = QDeliveryPolicy.deliveryPolicy;

        return from(deliveryPolicy)
            .select(Projections.constructor(DeliveryPolicyReadResponseDto.class,
                deliveryPolicy.deliveryPolicyId,
                deliveryPolicy.deliveryPrice,
                deliveryPolicy.createdAt,
                deliveryPolicy.freeDeliveryPrice))
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param deliveryPolicyId Long
     * @return DeliveryPolicyReadResponseDto
     */
    @Override
    public DeliveryPolicyReadResponseDto findDeliveryPolicyById(Long deliveryPolicyId) {
        QDeliveryPolicy deliveryPolicy = QDeliveryPolicy.deliveryPolicy;

        return from(deliveryPolicy)
            .select(Projections.constructor(DeliveryPolicyReadResponseDto.class,
                deliveryPolicy.deliveryPolicyId,
                deliveryPolicy.deliveryPrice,
                deliveryPolicy.createdAt,
                deliveryPolicy.freeDeliveryPrice))
            .where(deliveryPolicy.deliveryPolicyId.eq(deliveryPolicyId))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     *
     * @return DeliveryPolicyReadResponseDto
     */
    @Override
    public DeliveryPolicyReadResponseDto findCurrentPolicy() {
        QDeliveryPolicy deliveryPolicy = QDeliveryPolicy.deliveryPolicy;

        return from(deliveryPolicy)
            .select(Projections.constructor(DeliveryPolicyReadResponseDto.class,
                deliveryPolicy.deliveryPolicyId,
                deliveryPolicy.deliveryPrice,
                deliveryPolicy.createdAt,
                deliveryPolicy.freeDeliveryPrice))
            .orderBy(deliveryPolicy.createdAt.desc())
            .limit(1)
            .fetchOne();
    }
}
