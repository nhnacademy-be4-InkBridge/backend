package com.nhnacademy.inkbridge.backend.dto.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: WrappingCreateRequestDto.
 *
 * @author JBum
 * @version 2024/03/12
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WrappingCreateRequestDto {

    @NotNull(message = "포장지 이름을 입력해주세요.")
    @NotBlank(message = "포장지 이름이 공란입니다.")
    private String wrappingName;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0, message = "최소 0원이상의 가격으로 작성해주세요")
    private Long price;
}
