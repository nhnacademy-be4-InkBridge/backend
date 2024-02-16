package com.nhnacademy.inkbridge.backend.dto.pointpolicy;

import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: PointPolicyCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@Setter
@NoArgsConstructor
public class PointPolicyCreateRequestDto {
    @Min(0)
    private Long accumulatePoint;

    private Integer pointPolicyTypeId;
}
