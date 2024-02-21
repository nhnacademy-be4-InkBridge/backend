package com.nhnacademy.inkbridge.backend.dto.member;

import com.nhnacademy.inkbridge.backend.entity.MemberAuth;
import lombok.Builder;
import lombok.Getter;

/**
 * class: MemberGetLoginResponseDto.
 *
 * @author jeongbyeonghun
 * @version 2/20/24
 */
@Getter
public class MemberGetLoginResponseDto {

    String email;
    MemberAuth memberAuth;

    @Builder
    public MemberGetLoginResponseDto(String email, MemberAuth memberAuth) {
        this.email = email;
        this.memberAuth = memberAuth;
    }
}
