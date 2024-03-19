package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;

/**
 * class: MemberService.
 *
 * @author minseo
 * @version 2/15/24
 */
public interface MemberService {

    /**
     * 회원가입하는 메서드.
     *
     * @param memberCreateRequestDto 회원가입 폼 데이터
     */
    Member createMember(MemberCreateRequestDto memberCreateRequestDto);

    /**
     * auth 서버에서 로그인 인증시 필요한 로그인 정보를 가져오는 메서드.
     *
     * @param memberAuthLoginRequestDto 정보를 가져올 회원 로그인 정보
     * @return 회원 정보
     */
    MemberAuthLoginResponseDto loginInfoMember(MemberAuthLoginRequestDto memberAuthLoginRequestDto);

    /**
     * 로그인 성공시 가져올 회원 정보 메서드.
     *
     * @param memberId 정보를 가져올 회원 아이디
     * @return 회원 정보
     */
    MemberInfoResponseDto getMemberInfo(Long memberId);

    /**
     * 등록되어있는 OAuth 회원인지 아닌지 체크하는 메서드.
     *
     * @param id oauth 회원 식별 아이디
     * @return 체크 여부
     */
    boolean checkOAuthMember(String id);

    /**
     * OAUTH 로그인시 우리 서비스 로그인에 필요한 이메일을 가져오는 메서드.
     *
     * @param id oauth 회원 식별 아이디
     * @return oauth 회원 이메일
     */
    String getOAuthMemberEmail(String id);
    
    Boolean checkDuplicatedEmail(String email);

}
