package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.entity.MemberStatus;
import com.nhnacademy.inkbridge.backend.repository.MemberAuthRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberGradeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberStatusRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * class: MemberServiceImplTest.
 *
 * @author minseo
 * @version 2/16/24
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MemberAuthRepository memberAuthRepository;
    @Mock
    private MemberStatusRepository memberStatusRepository;
    @Mock
    private MemberGradeRepository memberGradeRepository;
    @InjectMocks
    private MemberServiceImpl memberService;

    private MemberCreateRequestDto requestDto;
    @BeforeEach
    void setUp() {
        requestDto = new MemberCreateRequestDto();
        requestDto.setMemberName("minseo");
        requestDto.setBirthday(LocalDate.of(1999, 7, 4));
        requestDto.setEmail("nhnacademy@dooray.com");
        requestDto.setPassword("P@ssw0rd123");
        requestDto.setPhoneNumber("01012345678");
    }

    @Test
    void createMember_success() {

        when(memberRepository.existsByEmail(anyString())).thenReturn(false);

        MemberAuth memberAuth = MemberAuth.create().memberAuthId(1).memberAuthName("MEMBER").build();
        MemberStatus memberStatus = MemberStatus.create().memberStatusId(1).memberStatusName("ACTIVE").build();
        MemberGrade memberGrade = MemberGrade.create().gradeId(1).standardAmount(100000L).pointRate(new BigDecimal(1)).grade("STANDARD").build();

        when(memberAuthRepository.findById(anyInt())).thenReturn(Optional.ofNullable(memberAuth));
        when(memberStatusRepository.findById(anyInt())).thenReturn(Optional.ofNullable(memberStatus));
        when(memberGradeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(memberGrade));

        memberService.createMember(requestDto);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

}