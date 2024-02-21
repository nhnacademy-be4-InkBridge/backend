package com.nhnacademy.inkbridge.backend.dto.member;

import com.nhnacademy.inkbridge.backend.entity.Member;
import lombok.Builder;
import lombok.Getter;

/**
 * class: MemberCreateResponseDto.
 *
 * @author jeongbyeonghun
 * @version 2/20/24
 */

@Getter
public class MemberCreateResponseDto {

    String memberName;
    String email;

    @Builder
    public MemberCreateResponseDto(Member member) {
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
    }

}
