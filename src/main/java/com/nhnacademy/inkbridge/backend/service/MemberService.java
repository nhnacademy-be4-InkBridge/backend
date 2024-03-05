package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;

/**
 * class: MemberService.
 *
 * @author minseo
 * @version 2/15/24
 */
public interface MemberService {

    void createMember(MemberCreateRequestDto memberCreateRequestDto);

    MemberAuthLoginResponseDto loginInfoMember(MemberAuthLoginRequestDto memberAuthLoginRequestDto);

    MemberInfoResponseDto getMemberInfo(Long memberId);
}
