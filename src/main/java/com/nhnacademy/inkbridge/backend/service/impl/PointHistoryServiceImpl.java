package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.PointHistory;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.entity.enums.PointHistoryReason;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.PointHistoryRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    private final MemberRepository memberRepository;
    private static final Integer REGISTER = 1;

    @Override
    public void accumulatePointAtSignup(Long memberId) {
        // 회원가입시 축하금 지급
        PointPolicyType pointType =
                pointPolicyTypeRepository.findById(REGISTER).orElseThrow(() -> new NotFoundException(
                        PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));
        PointPolicy pointPolicy =
                pointPolicyRepository.findById(Long.valueOf(pointType.getPointPolicyTypeId())).orElseThrow(
                        () -> new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_NOT_FOUND.getMessage()));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));

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
