package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberEmailRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberIdNoRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.facade.MemberFacade;
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: MemberControllerTest.
 *
 * @author devminseo
 * @version 3/20/24
 */
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private MemberFacade memberFacade;
    @MockBean
    private MemberService memberService;
    @MockBean
    private OrderFacade orderFacade;
    @MockBean
    private CouponService couponService;
    private ObjectMapper objectMapper;
    MemberCreateRequestDto memberCreateRequestDto;
    MemberAuthLoginRequestDto memberAuthLoginRequestDto;
    MemberEmailRequestDto memberEmailRequestDto;
    MemberAuthLoginResponseDto memberAuthLoginResponseDto;
    MemberInfoResponseDto memberInfoResponseDto;
    MemberIdNoRequestDto memberIdNoRequestDto;
    LocalDate today = LocalDate.now();
    @MockBean
    HttpServletRequest request;

    String uri = "/api/members";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        memberIdNoRequestDto = new MemberIdNoRequestDto();
        memberCreateRequestDto = new MemberCreateRequestDto();
        memberAuthLoginRequestDto = new MemberAuthLoginRequestDto();
        memberEmailRequestDto = new MemberEmailRequestDto();
        memberAuthLoginResponseDto = new MemberAuthLoginResponseDto(
                1L,
                "sa4777@naver.com",
                "$2a$10$ILNBmH6tPNBa8/WeZ4hvi.BHj4bcpUKWcCM/Zc2SLIHBgvForZdHq",
                new ArrayList<>()
        );
        memberInfoResponseDto = new MemberInfoResponseDto(
                1L,
                "이민서",
                "sa4777@naver.com",
                "01012345678",
                "1999-07-04",
                5000L,
                new ArrayList<>()
        );


        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("멤버 회원가입 성공")
    void member_create_success()throws Exception {
        ReflectionTestUtils.setField(memberCreateRequestDto,"email","sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 이메일 널")
    void member_create_email_null() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", null);
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일은 필수 입력 값 입니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 비밀번호 널")
    void member_create_password_null() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password",null);
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[비밀번호는 필수 입력 값 입니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 비밀번호 공백")
    void member_create_password_blank() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto, "email", "sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[비밀번호는 필수 입력 값 입니다.]"))
                .andDo(print());
    }


    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 이름 널")
    void member_create_memberName_null() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto,"email","sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName",null);
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이름은 필수 입력 값 입니다.]"))
                .andDo(print());
    }
    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 이름 널")
    void member_create_memberName_blank() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto,"email","sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이름은 필수 입력 값 입니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 생일 널")
    void member_create_birthday_null() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto,"email","sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", null);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","01012345678");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[생일은 필수 입력 값 입니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 핸드폰 번호 널")
    void member_create_phoneNumber_null() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto,"email","sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber",null);

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[핸드폰 번호는 필수 입력 값 입니다.]"))
                .andDo(print());
    }
    @Test
    @DisplayName("멤버 회원가입 실패 - 유효성 검사 실패 - 핸드폰 번호 공백")
    void member_create_phoneNumber_blank() throws Exception{
        ReflectionTestUtils.setField(memberCreateRequestDto,"email","sa4777@naver.com");
        ReflectionTestUtils.setField(memberCreateRequestDto,"password","$2a$10$Vg6NdhS0lnyQNe1FXg6cROGWVPgcyfDXl9ftA1pA6ni4aY3Hj");
        ReflectionTestUtils.setField(memberCreateRequestDto,"memberName","이민서");
        ReflectionTestUtils.setField(memberCreateRequestDto,"birthday", today);
        ReflectionTestUtils.setField(memberCreateRequestDto,"phoneNumber","");

        doNothing().when(memberFacade).signupFacade(memberCreateRequestDto);

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(memberCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[핸드폰 번호는 필수 입력 값 입니다.]"))
                .andDo(print());
    }


    @Test
    @DisplayName("로그인 정보 가져오기 성공")
    void auth_login_success()throws Exception {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto,"email","sa4777@naver.com");

        when(memberService.loginInfoMember(any())).thenReturn(memberAuthLoginResponseDto);

        mockMvc.perform(post(uri + "/login")
                        .content(objectMapper.writeValueAsString(memberAuthLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.memberId").value(memberAuthLoginResponseDto.getMemberId()))
                .andExpect(jsonPath("$.email").value(memberAuthLoginResponseDto.getEmail()))
                .andExpect(jsonPath("$.password").value(memberAuthLoginResponseDto.getPassword()));
    }

    @Test
    @DisplayName("로그인 정보 가져오기 실패 - 유효성 검사 실패 - 이메일 패턴")
    void auth_login_email_pattern()throws Exception {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto,"email","sa4777naver.com");

        when(memberService.loginInfoMember(any())).thenReturn(memberAuthLoginResponseDto);

        mockMvc.perform(post(uri + "/login")
                        .content(objectMapper.writeValueAsString(memberAuthLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일 형식이 틀렸습니다.]"))
                .andDo(print());
    }
    @Test
    @DisplayName("로그인 정보 가져오기 실패 - 유효성 검사 실패 - 이메일 널")
    void auth_login_email_null()throws Exception {
        ReflectionTestUtils.setField(memberAuthLoginRequestDto,"email",null);

        when(memberService.loginInfoMember(any())).thenReturn(memberAuthLoginResponseDto);

        mockMvc.perform(post(uri + "/login")
                        .content(objectMapper.writeValueAsString(memberAuthLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일은 필수 입력 값 입니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("이메일 중복체크 성공")
    void is_duplicated_email_success()throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto,"email","sa4777@naver.com");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(true);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }
    @Test
    @DisplayName("이메일 중복체크 실패")
    void is_duplicated_email_fail()throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto,"email","sa4777@naver.com");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(false);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));
    }
    @Test
    @DisplayName("이메일 중복체크 실패 - 유효성 검사 실패 - 이메일 패턴")
    void is_duplicated_email_pattern()throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto,"email","sa4777avcom.com");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(false);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일이 형식에 맞지 않습니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("이메일 중복체크 실패 - 유효성 검사 실패 - 이메일 블랭크")
    void is_duplicated_email_blank()throws Exception {
        ReflectionTestUtils.setField(memberEmailRequestDto,"email","");

        when(memberService.checkDuplicatedEmail(memberEmailRequestDto.getEmail())).thenReturn(false);

        mockMvc.perform(post(uri + "/checkEmail")
                        .content(objectMapper.writeValueAsString(memberEmailRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[이메일은 필수 입력 값 입니다.]"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 정보 가져오기 성공")
    void get_memberInfo_success()throws Exception {
        Long memberId = 1L;

        when(memberService.getMemberInfo(memberId)).thenReturn(memberInfoResponseDto);

        mockMvc.perform(get("/api/auth/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization-Id",memberId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberId").value(memberInfoResponseDto.getMemberId()))
                .andExpect(jsonPath("$.memberName").value(memberInfoResponseDto.getMemberName()))
                .andExpect(jsonPath("$.email").value(memberInfoResponseDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(memberInfoResponseDto.getPhoneNumber()))
                .andExpect(jsonPath("$.birthday").value(memberInfoResponseDto.getBirthday()))
                .andExpect(jsonPath("$.memberPoint").value(memberInfoResponseDto.getMemberPoint()));
    }

    @Test
    @DisplayName("소셜멤버 체크 성공")
    void check_oauthMember_success()throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto,"id","thisisID");

        when(memberService.checkOAuthMember(memberIdNoRequestDto.getId())).thenReturn(true);

        mockMvc.perform(post("/api/oauth/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("소셜멤버 체크 실패")
    void check_oauthMember_fail()throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto,"id","thisisID");

        when(memberService.checkOAuthMember(memberIdNoRequestDto.getId())).thenReturn(false);

        mockMvc.perform(post("/api/oauth/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("소셜멤버 체크 - 유효성 검사 실패")
    void check_oauthMember_fail_blank()throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto,"id","");

        when(memberService.checkOAuthMember(memberIdNoRequestDto.getId())).thenReturn(false);

        mockMvc.perform(post("/api/oauth/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("[아이디는 필수 입력 값 입니다.]"));
    }

    @Test
    @DisplayName("소셜멤버 이메일 가져오기 성공")
    void get_oauthEmail_success()throws Exception {
        String email = "sa4777@naver.com";
        ReflectionTestUtils.setField(memberIdNoRequestDto,"id","thisisID");

        when(memberService.getOAuthMemberEmail(memberIdNoRequestDto.getId())).thenReturn(email);

        mockMvc.perform(post("/api/oauth")
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(email));
    }
    @Test
    @DisplayName("소셜멤버 이메일 가져오기 - 유효성 검사 실패")
    void get_oauthEmail_fail()throws Exception {
        ReflectionTestUtils.setField(memberIdNoRequestDto,"id","");

        mockMvc.perform(post("/api/oauth")
                        .content(objectMapper.writeValueAsString(memberIdNoRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[아이디는 필수 입력 값 입니다.]"));
    }


}