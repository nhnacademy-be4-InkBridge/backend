package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.QPointPolicy;
import com.nhnacademy.inkbridge.backend.entity.QPointPolicyType;
import com.nhnacademy.inkbridge.backend.repository.custom.PointPolicyRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
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

    /**
     * 포인트 정책 유형 내역을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    @Override
    public List<PointPolicyReadResponseDto> findAllPointPolicyByTypeId(Integer pointPolicyTypeId) {
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
            .where(pointPolicyType.pointPolicyTypeId.eq(pointPolicyTypeId))
            .fetch();
    }


    /**
     * 현재 적용중인 포인트 정책 목록을 조회하는 메소드입니다. <br/> 변경 일자를 통해 가장 마지막에 등록한 날의 정책을 조회합니다. <br/> 같은 날 여러 번의
     * 변경이 이루어 진다면 가장 마지막에 변경된 정책을 조회합니다. <br/> 정렬 순서는 정책 유형의 id 번호 순으로 정렬됩니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    @Override
    public List<PointPolicyReadResponseDto> findAllCurrentPointPolicies() {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;
        QPointPolicy subPointPolicy = new QPointPolicy("subPointPolicy");
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicy)
            .select(Projections.constructor(PointPolicyReadResponseDto.class,
                pointPolicy.pointPolicyId,
                pointPolicyType.policyType,
                pointPolicy.accumulatePoint,
                pointPolicy.createdAt))
            .innerJoin(pointPolicyType)
            .on(pointPolicy.pointPolicyType.eq(pointPolicyType))
            .where(Expressions.list(pointPolicy.pointPolicyType, pointPolicy.pointPolicyId,
                pointPolicy.createdAt).in(
                JPAExpressions.select(subPointPolicy.pointPolicyType,
                        subPointPolicy.pointPolicyId.max(),
                        subPointPolicy.createdAt.max())
                    .from(subPointPolicy)
                    .groupBy(subPointPolicy.pointPolicyType)
            ))
            .orderBy(pointPolicyType.pointPolicyTypeId.asc())
            .fetch();
    }

    /**
     * 포인트 정책 유형에 맞는 현재 적용중인 정책을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    @Override
    public PointPolicyReadResponseDto findCurrentPointPolicy(Integer pointPolicyTypeId) {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;
        QPointPolicyType pointPolicyType = QPointPolicyType.pointPolicyType;

        return from(pointPolicy)
            .select(Projections.constructor(PointPolicyReadResponseDto.class,
                pointPolicy.pointPolicyId,
                pointPolicyType.policyType,
                pointPolicy.accumulatePoint,
                pointPolicy.createdAt))
            .innerJoin(pointPolicyType)
            .on(pointPolicy.pointPolicyType.eq(pointPolicyType))
            .where(pointPolicyType.pointPolicyTypeId.eq(pointPolicyTypeId))
            .orderBy(pointPolicy.pointPolicyId.desc())
            .limit(1L)
            .fetchOne();
    }


}
