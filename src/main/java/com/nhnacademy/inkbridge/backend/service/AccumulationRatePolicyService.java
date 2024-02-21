package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import java.util.List;

/**
 * class: AccumulationRatePolicyService.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public interface AccumulationRatePolicyService {

    List<AccumulationRatePolicyReadResponseDto> getAccumulationRatePolicies();

    AccumulationRatePolicyReadResponseDto getAccumulationRatePolicy(Long accumulationRatePolicyId);

    AccumulationRatePolicyReadResponseDto getCurrentAccumulationRatePolicy();

    void createAccumulationRatePolicy(AccumulationRatePolicyCreateRequestDto requestDto);
}
