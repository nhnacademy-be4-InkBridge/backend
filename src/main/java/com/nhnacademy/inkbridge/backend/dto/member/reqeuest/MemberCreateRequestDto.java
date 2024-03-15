package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * class: MemberCreateRequestDto.
 *
 * @author minseo
 * @version 2/15/24
 */
@Getter
@NoArgsConstructor
public class MemberCreateRequestDto {
    private String email;

    private String password;

    private String memberName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String phoneNumber;
}
