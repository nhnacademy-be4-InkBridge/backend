package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberEmailResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import com.nhnacademy.inkbridge.backend.entity.PointHistory;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.entity.enums.PointHistoryReason;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberAuthRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberGradeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.PointHistoryRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: MemberServiceImpl.
 *
 * @author minseo
 * @version 2/15/24
 */
@Service("memberService")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberAuthRepository memberAuthRepository;
    private final MemberStatusRepository memberStatusRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final PointPolicyRepository pointPolicyRepository;
    private final PointPolicyTypeRepository pointPolicyTypeRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private static final Integer REGISTER = 1;
    private static final Integer ONE = 1;
    private static final Integer SOCIAL = 3;
    private static final Integer DORMANT = 2;
    private static final Integer CLOSE = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createMember(MemberCreateRequestDto memberCreateRequestDto) {

        if (memberRepository.existsByEmail(memberCreateRequestDto.getEmail())) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_ALREADY_EXIST.getMessage());
        }

        MemberAuth memberAuth = memberAuthRepository.findById(ONE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));
        MemberAuth socialAuth = memberAuthRepository.findById(SOCIAL)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));
        MemberStatus memberStatus = memberStatusRepository.findById(ONE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));
        MemberGrade memberGrade = memberGradeRepository.findById(ONE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));

        String email = memberCreateRequestDto.getEmail();
        if (email.startsWith("SOCIAL ")) {
            memberAuth = socialAuth;
            email = email.substring(7);
        }


        // 회원가입시 축하금 지급
        PointPolicyType pointType =
                pointPolicyTypeRepository.findById(REGISTER).orElseThrow(() -> new NotFoundException(
                        PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));
        PointPolicy pointPolicy =
                pointPolicyRepository.findByPointPolicyType(pointType).orElseThrow(
                        () -> new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_NOT_FOUND.getMessage()));

        Member member = Member.create()
                .createdAt(LocalDateTime.now())
                .memberAuth(memberAuth)
                .memberGrade(memberGrade)
                .memberName(memberCreateRequestDto.getMemberName())
                .birthday(memberCreateRequestDto.getBirthday())
                .password(memberCreateRequestDto.getPassword())
                .phoneNumber(memberCreateRequestDto.getPhoneNumber())
                .memberStatus(memberStatus)
                .email(email)
                .memberPoint(pointPolicy.getAccumulatePoint())
                .build();

        // 포인트 내역 추가
        PointHistory pointHistory = PointHistory.builder()
                .reason(PointHistoryReason.REGISTER_MSG.getMessage())
                .point(pointPolicy.getAccumulatePoint())
                .accruedAt(LocalDateTime.now())
                .member(member)
                .build();

        pointHistoryRepository.save(pointHistory);
        memberRepository.save(member);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAuthLoginResponseDto loginInfoMember(MemberAuthLoginRequestDto memberAuthLoginRequestDto) {
        Member member = memberRepository.findByEmail(memberAuthLoginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));
        MemberStatus sleep = memberStatusRepository.findById(DORMANT)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_STATUS_NOT_FOUND.getMessage()));
        MemberStatus active = memberStatusRepository.findById(ONE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_STATUS_NOT_FOUND.getMessage()));
        MemberStatus close = memberStatusRepository.findById(CLOSE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_STATUS_NOT_FOUND.getMessage()));

        member.updateLastLoginDate();

        if (member.getMemberStatus().getMemberStatusName().equals(sleep.getMemberStatusName())) {
            member.updateActive(active);
        }
        if (member.getMemberStatus().getMemberStatusName().equals(close.getMemberStatusName())) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }

        return memberRepository.findByMemberAuth(memberAuthLoginRequestDto.getEmail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        return memberRepository.findByMemberInfo(memberId).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkOAuthMember(String id) {
        return memberRepository.existsByPassword(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOAuthMemberEmail(String id) {
        log.info("email start ->");
        Optional<MemberEmailResponseDto> email = memberRepository.findByPassword(id);
        log.info("email -> {}", email);
        if (email.isEmpty()) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }
        return email.get().getEmail();
    }

    @Override
    public Boolean checkDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
