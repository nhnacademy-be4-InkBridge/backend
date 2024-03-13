package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Email(message = "이메일 형식이 틀렸습니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotNull(message = "이름은 필수 입력 값입니다.")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String memberName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "생일은 필수 입력 값입니다.")
    private LocalDate birthday;

    @NotNull(message = "핸드폰 번호는 필수 입력 값입니다.")
    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    private String phoneNumber;
}
