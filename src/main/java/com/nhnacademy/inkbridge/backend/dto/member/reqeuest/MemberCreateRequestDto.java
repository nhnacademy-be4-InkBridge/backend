package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: MemberCreateRequestDto.
 *
 * @author minseo
 * @version 2/15/24
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberCreateRequestDto {
    @NotNull(message = "이름은 필수 입력 값입니다.")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String memberName;
    @NotNull(message = "핸드폰 번호는' 필수 입력 값입니다.")
    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    private String phoneNumber;
    @Email(message = "이메일 형식이 틀렸습니다.")
    private String email;
    @NotNull(message = "생일은 필수 입력 값입니다.")
    private LocalDate birthday;
    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자 영문 대 소문자, 숫자 , 특수문자를 사용하세요.")
    private String password;
}
