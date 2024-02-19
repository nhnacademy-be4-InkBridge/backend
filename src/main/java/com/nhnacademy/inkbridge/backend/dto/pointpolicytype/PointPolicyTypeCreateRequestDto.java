package com.nhnacademy.inkbridge.backend.dto.pointpolicytype;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 20)
    @NotBlank
    private String policyType;
}
