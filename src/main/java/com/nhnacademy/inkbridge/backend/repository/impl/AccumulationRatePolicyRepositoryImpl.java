package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.AccumulationRatePolicy;
import com.nhnacademy.inkbridge.backend.entity.QAccumulationRatePolicy;
import com.nhnacademy.inkbridge.backend.repository.custom.AccumulationRatePolicyRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: AccumulationRatePolicyRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public class AccumulationRatePolicyRepositoryImpl extends QuerydslRepositorySupport implements
    AccumulationRatePolicyRepositoryCustom {

    public AccumulationRatePolicyRepositoryImpl() {
        super(AccumulationRatePolicy.class);
    }

    /**
     * 적립율 정책 전체 내역을 조회하는 메소드입니다.
     *
     * @return List - AccumulationRatePolicyReadResponseDto
     */
    @Override
    public List<AccumulationRatePolicyReadResponseDto> findAllAccumulationRatePolicies() {
        QAccumulationRatePolicy accumulationRatePolicy = QAccumulationRatePolicy.accumulationRatePolicy;

        return from(accumulationRatePolicy)
            .select(Projections.constructor(AccumulationRatePolicyReadResponseDto.class,
                accumulationRatePolicy.accumulationRatePolicyId,
                accumulationRatePolicy.accumulationRate,
                accumulationRatePolicy.createdAt))
            .orderBy(accumulationRatePolicy.accumulationRatePolicyId.desc())
            .fetch();
    }

    /**
     * 적립율 정책 id로 정책 내역을 조회하는 메소드입니다.
     *
     * @param accumulationRatePolicyId Long
     * @return AccumulationRatePolicyReadResponseDto
     */
    @Override
    public AccumulationRatePolicyReadResponseDto findAccumulationRatePolicy(
        Long accumulationRatePolicyId) {
        QAccumulationRatePolicy accumulationRatePolicy = QAccumulationRatePolicy.accumulationRatePolicy;

        return from(accumulationRatePolicy)
            .select(Projections.constructor(AccumulationRatePolicyReadResponseDto.class,
                accumulationRatePolicy.accumulationRatePolicyId,
                accumulationRatePolicy.accumulationRate,
                accumulationRatePolicy.createdAt))
            .where(accumulationRatePolicy.accumulationRatePolicyId.eq(accumulationRatePolicyId))
            .fetchOne();
    }

    /**
     * 현재 적용되는 적립율 정책을 조회하는 메소드입니다.
     *
     * @return AccumulationRatePolicyReadResponseDto
     */
    @Override
    public AccumulationRatePolicyReadResponseDto findCurrentAccumulationRatePolicy() {
        QAccumulationRatePolicy accumulationRatePolicy = QAccumulationRatePolicy.accumulationRatePolicy;

        return from(accumulationRatePolicy)
            .select(Projections.constructor(AccumulationRatePolicyReadResponseDto.class,
                accumulationRatePolicy.accumulationRatePolicyId,
                accumulationRatePolicy.accumulationRate,
                accumulationRatePolicy.createdAt))
            .orderBy(accumulationRatePolicy.accumulationRatePolicyId.desc())
            .limit(1)
            .fetchOne();
    }
}
