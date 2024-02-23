package com.nhnacademy.inkbridge.backend.dto.deliverypolicy;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: DeliveryPolicyCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@Getter
@NoArgsConstructor
@Setter
public class DeliveryPolicyCreateRequestDto {

    @NotNull(message = "배송비는 필수 입력 항목입니다.")
    @Min(value = 0L, message = "배송비는 음수일 수 없습니다.")
    private Long deliveryPrice;
}
