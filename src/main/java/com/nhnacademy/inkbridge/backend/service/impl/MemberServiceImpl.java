package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.member.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberGetLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberGetLoginResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import com.nhnacademy.inkbridge.backend.enums.MemberAuthMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberGradeMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberStatusMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberAuthRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberGradeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberStatusRepository;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberAuthRepository memberAuthRepository;
    private final MemberStatusRepository memberStatusRepository;
    private final MemberGradeRepository memberGradeRepository;

    /**
     * DB에 새로운 Member를 추가하는 메서드입니다.
     *
     * @param memberCreateRequestDto
     */
    @Override
    @Transactional
    public MemberCreateResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto) {

        if (memberRepository.existsByEmail(memberCreateRequestDto.getEmail())) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_ALREADY_EXIST.toString());
        }

        MemberAuth memberAuth = memberAuthRepository.findById(1)
            .orElseThrow(() -> new NotFoundException(
                MemberAuthMessageEnum.MEMBER_AUTH_NOT_FOUND.getMessage()));
        MemberStatus memberStatus = memberStatusRepository.findById(1)
            .orElseThrow(() -> new NotFoundException(
                MemberStatusMessageEnum.MEMBER_STATUS_NOT_FOUND.getMessage()));
        MemberGrade memberGrade = memberGradeRepository.findById(1)
            .orElseThrow(() -> new NotFoundException(
                MemberGradeMessageEnum.MEMBER_GRADE_NOT_FOUND.getMessage()));
        String password = memberCreateRequestDto.getPassword();

        if (!"payco".equals(password)) {
            password = passwordEncoder.encode(password);
        }

        Member member = Member.create()
            .memberName(memberCreateRequestDto.getMemberName())
            .phoneNumber(memberCreateRequestDto.getPhoneNumber())
            .email(memberCreateRequestDto.getEmail())
            .birthday(memberCreateRequestDto.getBirthday())
            .password(password)
            .createdAt(LocalDateTime.now())
            .memberPoint(0L)
            .memberAuth(memberAuth)
            .memberStatus(memberStatus)
            .memberGrade(memberGrade)
            .build();

        return MemberCreateResponseDto.builder().member(memberRepository.save(member)).build();
    }

    @Override
    @Transactional
    public MemberGetLoginResponseDto login(MemberGetLoginRequestDto memberGetLoginRequestDto) {

        String password = memberGetLoginRequestDto.getPassword();

        if (!"payco".equals(password)) {
            password = passwordEncoder.encode(password);
        }
        Member member = memberRepository.findByEmailAndPassword(memberGetLoginRequestDto.getEmail(),
                password)
            .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        member.setLastLoginDate(LocalDate.now());
        member = memberRepository.save(member);
        return MemberGetLoginResponseDto.builder().email(member.getEmail())
            .memberAuth(member.getMemberAuth()).email(member.getEmail()).build();
    }


}
