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

    /**
     * {@inheritDoc}
     */
    @Override
    public void createMember(MemberCreateRequestDto memberCreateRequestDto) {

        if (memberRepository.existsByEmail(memberCreateRequestDto.getEmail())) {
            log.error("이미 존재하는 이메일 입니다.");
            throw new NotFoundException(MemberMessageEnum.MEMBER_ALREADY_EXIST.getMessage());
        }

        MemberAuth memberAuth = memberAuthRepository.findById(1).orElse(null);
        MemberAuth socialAuth = memberAuthRepository.findById(3).orElse(null);
        MemberStatus memberStatus = memberStatusRepository.findById(1).orElse(null);
        MemberGrade memberGrade = memberGradeRepository.findById(1).orElse(null);

        if (Objects.isNull(memberAuth) || Objects.isNull(memberStatus) || Objects.isNull(memberGrade)||Objects.isNull(socialAuth)) {
            throw new IllegalArgumentException();
        }
        String email = memberCreateRequestDto.getEmail();
        if (email.startsWith("SOCIAL ")) {
            memberAuth = socialAuth;
            email = email.substring(7);
            log.info("email -> {}",email);
            log.info("auth -> {}",memberAuth.getMemberAuthName());
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


        memberRepository.save(member);

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAuthLoginResponseDto loginInfoMember(MemberAuthLoginRequestDto memberAuthLoginRequestDto) {
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
