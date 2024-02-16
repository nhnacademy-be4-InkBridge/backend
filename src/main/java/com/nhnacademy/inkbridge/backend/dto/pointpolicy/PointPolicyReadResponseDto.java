package com.nhnacademy.inkbridge.backend.dto.pointpolicy;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: PointPolicyReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@Setter
@NoArgsConstructor
public class PointPolicyReadResponseDto {

    private Long pointPolicyId;
    private String policyType;
    private Long accumulatePoint;
    private LocalDate createdAt;
}
