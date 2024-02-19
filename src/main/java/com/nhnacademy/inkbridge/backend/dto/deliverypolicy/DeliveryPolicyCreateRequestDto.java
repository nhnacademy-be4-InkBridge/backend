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

    @NotNull
    @Min(0)
    private Long deliveryPrice;
}
