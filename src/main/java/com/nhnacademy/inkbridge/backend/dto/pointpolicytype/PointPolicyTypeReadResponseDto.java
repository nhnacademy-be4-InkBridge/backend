package com.nhnacademy.inkbridge.backend.dto.pointpolicytype;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: PointPolicyTypeReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointPolicyTypeReadResponseDto {

    private Integer pointPolicyTypeId;
    private String policyType;

}
