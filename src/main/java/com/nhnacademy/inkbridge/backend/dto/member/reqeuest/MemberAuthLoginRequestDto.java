package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberAuthLoginRequestDto.
 *
 * @author devminseo
 * @version 2/27/24
 */
@Getter
@NoArgsConstructor
public class MemberAuthLoginRequestDto {
    @Email
    private String email;
}
