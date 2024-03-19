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
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberAuthRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberGradeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberStatusRepository;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import java.time.LocalDateTime;
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
    private static final Integer ONE = 1;
    private static final Integer THREE = 3;
    private static final String SOCIAL = "SOCIAL ";

    /**
     * {@inheritDoc}
     */
    @Override
    public Member createMember(MemberCreateRequestDto memberCreateRequestDto) {

        if (memberRepository.existsByEmail(memberCreateRequestDto.getEmail())) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_ALREADY_EXIST.getMessage());
        }

        MemberAuth memberAuth = memberAuthRepository.findById(ONE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));
        MemberAuth socialAuth = memberAuthRepository.findById(THREE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));
        MemberStatus memberStatus = memberStatusRepository.findById(ONE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));
        MemberGrade memberGrade = memberGradeRepository.findById(ONE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));

        String email = memberCreateRequestDto.getEmail();
        if (email.startsWith(SOCIAL)) {
            memberAuth = socialAuth;
            email = email.substring(7);
        }


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
                .memberPoint(0L)
                .build();

        return memberRepository.save(member);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAuthLoginResponseDto loginInfoMember(MemberAuthLoginRequestDto memberAuthLoginRequestDto) {
        Member member = memberRepository.findByEmail(memberAuthLoginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));

        MemberStatus close = memberStatusRepository.findById(THREE)
                .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_STATUS_NOT_FOUND.getMessage()));

        member.updateLastLoginDate();

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
