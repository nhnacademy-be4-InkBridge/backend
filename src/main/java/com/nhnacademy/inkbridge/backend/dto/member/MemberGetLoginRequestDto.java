package com.nhnacademy.inkbridge.backend.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: MemberGetLoginRequestDto.
 *
 * @author jeongbyeonghun
 * @version 2/20/24
 */
@NoArgsConstructor
@Getter
@Setter
public class MemberGetLoginRequestDto {
    String password;
    String email;
}
