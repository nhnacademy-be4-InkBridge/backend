package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.PointHistory;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.entity.enums.PointHistoryReason;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PointHistoryRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: PointHistoryServiceImpl.
 *
 * @author devminseo
 * @version 3/19/24
 */
@Service("pointHistoryService")
@RequiredArgsConstructor
@Transactional
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointPolicyRepository pointPolicyRepository;
    private final PointPolicyTypeRepository pointPolicyTypeRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void accumulatePointAtSignup(Member member, Integer pointTypeId) {
        // 회원가입시 축하금 지급
        PointPolicyType pointType =
                pointPolicyTypeRepository.findById(pointTypeId).orElseThrow(() -> new NotFoundException(
                        PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));
        PointPolicy pointPolicy =
                pointPolicyRepository.findByPointPolicyType(pointType).orElseThrow(
                        () -> new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_NOT_FOUND.getMessage()));

        member.updateMemberPoint(pointPolicy.getAccumulatePoint());

        // 포인트 내역 추가
        PointHistory pointHistory = PointHistory.builder()
                .reason(PointHistoryReason.REGISTER_MSG.getMessage())
                .point(pointPolicy.getAccumulatePoint())
                .accruedAt(LocalDateTime.now())
                .member(member)
                .build();

        pointHistoryRepository.save(pointHistory);

    }
}
