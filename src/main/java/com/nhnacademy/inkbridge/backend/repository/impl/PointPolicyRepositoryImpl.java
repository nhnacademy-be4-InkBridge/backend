package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.QPointPolicy;
import com.nhnacademy.inkbridge.backend.entity.QPointPolicyType;
import com.nhnacademy.inkbridge.backend.repository.custom.PointPolicyRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: PointPolicyRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
public class PointPolicyRepositoryImpl extends QuerydslRepositorySupport implements
    PointPolicyRepositoryCustom {

    public PointPolicyRepositoryImpl() {
        super(PointPolicy.class);
    }

    /**
     * 포인트 정책 조회 메소드 입니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    @Override
    public List<PointPolicyReadResponseDto> findAllPointPolicyBy() {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicy)
            .innerJoin(pointPolicyType)
            .on(pointPolicy.pointPolicyType.eq(pointPolicyType))
            .select(Projections.constructor(PointPolicyReadResponseDto.class,
                pointPolicy.pointPolicyId,
                pointPolicyType.policyType,
                pointPolicy.accumulatePoint,
                pointPolicy.createdAt))
            .fetch();
    }
}
