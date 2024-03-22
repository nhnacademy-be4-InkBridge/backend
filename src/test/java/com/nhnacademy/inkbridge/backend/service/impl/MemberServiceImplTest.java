package com.nhnacademy.inkbridge.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: MemberServiceImplTest.
 *
 * @author devminseo
 * @version 3/21/24
 */
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberAuthRepository memberAuthRepository;
    @Mock
    MemberStatusRepository memberStatusRepository;
    @Mock
    MemberGradeRepository memberGradeRepository;
    MemberCreateRequestDto memberCreateRequestDto;
    MemberAuthLoginRequestDto memberAuthLoginRequestDto;
    MemberAuthLoginResponseDto memberAuthLoginResponseDto;
    MemberAuth memberAuth;
    MemberStatus memberStatus;
    MemberGrade memberGrade;
    Member member;
    ArgumentCaptor<Member> captor;

    @BeforeEach
    void setUp() {
        captor = ArgumentCaptor.forClass(Member.class);
        memberCreateRequestDto = new MemberCreateRequestDto();
        memberAuthLoginRequestDto = new MemberAuthLoginRequestDto();

        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto, "password",
            "$2a$10$ILNBmH6tPNBa8/WeZ4hvi.BHj4bcpUKWcCM/Zc2SLIHBgvForZdHq");
        ReflectionTestUtils.setField(memberCreateRequestDto, "memberName", "이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto, "birthday", LocalDate.now());
        ReflectionTestUtils.setField(memberCreateRequestDto, "phoneNumber", "01012345678");

        memberAuth = new MemberAuth(1, "ROLE_MEMBER");
        memberStatus = new MemberStatus(1, "ACTIVE");
        memberGrade = new MemberGrade(1, "STANDARD", BigDecimal.ZERO, 0L);
        member = Member.create().createdAt(LocalDateTime.now()).memberAuth(memberAuth)
            .memberGrade(memberGrade)
            .memberName(memberCreateRequestDto.getMemberName())
            .birthday(memberCreateRequestDto.getBirthday())
            .password(memberCreateRequestDto.getPassword())
            .phoneNumber(memberCreateRequestDto.getPhoneNumber())
            .memberStatus(memberStatus).email(memberCreateRequestDto.getEmail()).memberPoint(0L)
            .build();

        memberAuthLoginResponseDto =
            new MemberAuthLoginResponseDto(1L, "sa4777@naver.com", "password", new ArrayList<>());
    }

    @Test
    @DisplayName("멤버 생성")
    void createMember_success() {
        when(memberRepository.existsByEmail(any())).thenReturn(false);
        when(memberAuthRepository.findById(any())).thenReturn(Optional.of(memberAuth));
        when(memberStatusRepository.findById(any())).thenReturn(Optional.of(memberStatus));
        when(memberGradeRepository.findById(any())).thenReturn(Optional.of(memberGrade));

        when(memberRepository.save(any())).thenReturn(member);

        memberService.createMember(memberCreateRequestDto);

        verify(memberRepository, times(1)).save(captor.capture());

        String email = captor.getValue().getEmail();

        assertThat(memberCreateRequestDto.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("멤버 생성 - 소셜 회원")
    void createMember_success_social() {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "SOCIAL sa4777@naver.com");

        when(memberRepository.existsByEmail(any())).thenReturn(false);
        when(memberAuthRepository.findById(any())).thenReturn(
            Optional.of(memberAuth = new MemberAuth(3, "ROLE_SOCIAL")));
        when(memberStatusRepository.findById(any())).thenReturn(Optional.of(memberStatus));
        when(memberGradeRepository.findById(any())).thenReturn(Optional.of(memberGrade));

        if (memberCreateRequestDto.getEmail().startsWith("SOCIAL ")) {
            ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        }

        when(memberRepository.save(any())).thenReturn(member);

        memberService.createMember(memberCreateRequestDto);

        verify(memberRepository, times(1)).save(captor.capture());

        MemberAuth socialAuth = captor.getValue().getMemberAuth();

        assertThat(memberAuth.getMemberAuthName()).isEqualTo(socialAuth.getMemberAuthName());
    }

    @Test
    @DisplayName("멤버 생성 실패 - 이메일 이미 존재하는 경우")
    void createMember_fail() {
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");

        when(memberRepository.existsByEmail(any())).thenReturn(true);

        assertThatThrownBy(() -> memberService.createMember(memberCreateRequestDto)).isInstanceOf(
                NotFoundException.class)
            .hasMessageContaining(MemberMessageEnum.MEMBER_ALREADY_EXIST.getMessage());
    }

    @Test
    @DisplayName("로그인 정보 가져오기 성공")
    void login_InfoMember_success() {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto, "email", "sa4777@naver.com");

        when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
        when(memberStatusRepository.findById(any())).thenReturn(
            Optional.of(memberStatus = new MemberStatus(3, "CLOSE")));

        Assertions.assertNotEquals(member.getMemberStatus().getMemberStatusName(),
            memberStatus.getMemberStatusName());

        when(memberRepository.findByMemberAuth(anyString())).thenReturn(memberAuthLoginResponseDto);

        MemberAuthLoginResponseDto result = memberService.loginInfoMember(
            memberAuthLoginRequestDto);

        assertThat(result.getMemberId()).isEqualTo(memberAuthLoginResponseDto.getMemberId());
        assertThat(result.getPassword()).isEqualTo(memberAuthLoginResponseDto.getPassword());
        assertThat(result.getEmail()).isEqualTo(memberAuthLoginResponseDto.getEmail());
        assertThat(result.getRole()).isEqualTo(memberAuthLoginResponseDto.getRole());

        verify(memberRepository, times(1)).findByMemberAuth("sa4777@naver.com");

    }

}