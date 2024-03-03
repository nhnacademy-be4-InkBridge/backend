package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
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
import lombok.RequiredArgsConstructor;
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
    private final MemberAuthRepository memberAuthRepository;
    private final MemberStatusRepository memberStatusRepository;
    private final MemberGradeRepository memberGradeRepository;

    /**
     * DB에 새로운 Member를 추가하는 메서드입니다.
     *
     * @param memberCreateRequestDto
     */
    @Override
    public void createMember(MemberCreateRequestDto memberCreateRequestDto) {

        if (memberRepository.existsByEmail(memberCreateRequestDto.getEmail())) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_ALREADY_EXIST.toString());
        }

        MemberAuth memberAuth = memberAuthRepository.findById(1).orElse(null);
        MemberStatus memberStatus = memberStatusRepository.findById(1).orElse(null);
        MemberGrade memberGrade = memberGradeRepository.findById(1).orElse(null);

        if (Objects.isNull(memberAuth) || Objects.isNull(memberStatus) || Objects.isNull(memberGrade)) {
            throw new IllegalArgumentException();
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
                .email(memberCreateRequestDto.getEmail())
                .memberPoint(0L)
                .build();

        memberRepository.save(member);

    }

    @Override
    public MemberAuthLoginResponseDto loginInfoMember(MemberAuthLoginRequestDto memberAuthLoginRequestDto) {
        return memberRepository.findByMemberAuth(memberAuthLoginRequestDto.getEmail());
    }

    @Override
    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        return memberRepository.findByMemberInfo(memberId).orElse(null);
    }

}
