package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.member.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberGetLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberGetLoginResponseDto;

/**
 * class: MemberService.
 *
 * @author minseo
 * @version 2/15/24
 */
public interface MemberService {

    MemberCreateResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto);

    MemberGetLoginResponseDto login(MemberGetLoginRequestDto memberGetLoginRequestDto);
}
