package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import java.util.List;

/**
 * class: AccumulationRatePolicyRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public interface AccumulationRatePolicyRepositoryCustom {

    List<AccumulationRatePolicyReadResponseDto> findAllAccumulationRatePolicies();

    AccumulationRatePolicyReadResponseDto findAccumulationRatePolicy(Long accumulationRatePolicyId);

    AccumulationRatePolicyReadResponseDto findCurrentAccumulationRatePolicy();
}
