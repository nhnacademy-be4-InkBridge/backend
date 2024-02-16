package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.MemberCreateRequestDto;

/**
 * class: MemberService.
 *
 * @author minseo
 * @version 2/15/24
 */
public interface MemberService {

    public void createMember(MemberCreateRequestDto memberCreateRequestDto);
}
