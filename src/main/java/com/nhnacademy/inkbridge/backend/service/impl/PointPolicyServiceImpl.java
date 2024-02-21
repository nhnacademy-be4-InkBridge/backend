package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: PointPolicyServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Service
@RequiredArgsConstructor
public class PointPolicyServiceImpl implements PointPolicyService {

    private final PointPolicyRepository pointPolicyRepository;
    private final PointPolicyTypeRepository pointPolicyTypeRepository;

    /**
     * 포인트 정책 전체 조회 메서드 입니다.
     *
     * @return PointPolicyReadResponseDto
     */
    @Override
    @Transactional(readOnly = true)
    public List<PointPolicyReadResponseDto> getPointPolicies() {
        return pointPolicyRepository.findAllPointPolicyBy();
    }

    /**
     * 포인트 정책 생성 메서드 입니다.
     *
     * @param pointPolicyCreateRequestDto PointPolicyCreateRequestDto
     * @throws NotFoundException 포인트 정책 유형이 존재하지 않을 경우
     */
    @Override
    @Transactional
    public void createPointPolicy(PointPolicyCreateRequestDto pointPolicyCreateRequestDto) {
        PointPolicyType pointPolicyType = pointPolicyTypeRepository.findById(
            pointPolicyCreateRequestDto.getPointPolicyTypeId()
        ).orElseThrow(() -> new NotFoundException(
            PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));

        pointPolicyRepository.save(PointPolicy.builder()
            .accumulatePoint(pointPolicyCreateRequestDto.getAccumulatePoint())
            .pointPolicyType(pointPolicyType)
            .createdAt(LocalDate.now())
            .build());
    }

    /**
     * 포인트 정책 유형 Id로 포인트 정책 내역 리스트를 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return List - PointPolicyReadResponseDto
     * @throws NotFoundException pointPolicyTypeId가 존재하지 않는 경우
     */
    @Transactional(readOnly = true)
    @Override
    public List<PointPolicyReadResponseDto> getPointPoliciesByTypeId(Integer pointPolicyTypeId) {
        if (!pointPolicyTypeRepository.existsById(pointPolicyTypeId)) {
            throw new NotFoundException(
                PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage());
        }

        return pointPolicyRepository.findAllPointPolicyByTypeId(pointPolicyTypeId);
    }

    /**
     * 현재 적용중인 포인트 정책 목록을 조회하는 메소드입니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    @Override
    public List<PointPolicyReadResponseDto> getCurrentPointPolicies() {
        return pointPolicyRepository.findAllCurrentPointPolicies();
    }

    /**
     * 포인트 정책 유형의 현재 적용중인 정책을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     * @throws NotFoundException 포인트 정책 유형이 존재하지 않을 때
     */
    @Override
    public PointPolicyReadResponseDto getCurrentPointPolicy(Integer pointPolicyTypeId) {
        if (!pointPolicyTypeRepository.existsById(pointPolicyTypeId)) {
            throw new NotFoundException(
                PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage());
        }

        return pointPolicyRepository.findCurrentPointPolicy(pointPolicyTypeId);
    }
}
