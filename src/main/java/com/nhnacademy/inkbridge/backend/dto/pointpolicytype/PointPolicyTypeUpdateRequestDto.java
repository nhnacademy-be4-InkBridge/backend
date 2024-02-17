package com.nhnacademy.inkbridge.backend.dto.pointpolicytype;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: PointPolicyTypeUpdateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */

@Getter
@Setter
@NoArgsConstructor
public class PointPolicyTypeUpdateRequestDto {

    private Integer pointPolicyTypeId;

    @NotBlank
    private String policyType;
}
