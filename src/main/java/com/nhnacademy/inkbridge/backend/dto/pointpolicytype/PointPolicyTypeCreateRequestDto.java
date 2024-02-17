package com.nhnacademy.inkbridge.backend.dto.pointpolicytype;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: PointPolicyTypeCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@Setter
@NoArgsConstructor
public class PointPolicyTypeCreateRequestDto {
    @NotBlank
    private String policyType;
}
