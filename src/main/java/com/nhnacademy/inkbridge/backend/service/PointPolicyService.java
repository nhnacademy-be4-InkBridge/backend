package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import java.util.List;

/**
 * class: PointPolicyService.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
public interface PointPolicyService {

    List<PointPolicyReadResponseDto> getPointPolicies();

    void createPointPolicy(PointPolicyCreateRequestDto pointPolicyCreateRequestDto);

    List<PointPolicyReadResponseDto> getPointPoliciesByTypeId(Integer pointPolicyTypeId);

    List<PointPolicyReadResponseDto> getCurrentPointPolicies();

    PointPolicyReadResponseDto getCurrentPointPolicy(Integer pointPolicyTypeId);
}
