package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import java.util.List;

/**
 * class: AccumulationRatePolicyService.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public interface AccumulationRatePolicyService {

    /**
     * 적립율 정책 전체 내역을 조회하는 메소드입니다.
     *
     * @return List - AccumulationRatePolicyReadResponseDto
     */
    List<AccumulationRatePolicyReadResponseDto> getAccumulationRatePolicies();

    /**
     * 적립율 정책 id로 내역을 조회하는 메소드입니다.
     *
     * @param accumulationRatePolicyId Long
     * @return AccumulationRatePolicyReadResponseDto
     * @throws NotFoundException 적립률 정책 id에 일치하는 정책이 존재하지 않는 경우
     */
    AccumulationRatePolicyReadResponseDto getAccumulationRatePolicy(Long accumulationRatePolicyId);

    /**
     * 현재 적용되는 적립율 정책을 조회하는 메소드입니다.
     *
     * @return AccumulationRatePolicyReadResponseDto
     */
    AccumulationRatePolicyReadResponseDto getCurrentAccumulationRatePolicy();

    /**
     * 적립율 정책을 생성하는 메소드입니다.
     *
     * @param requestDto AccumulationRatePolicyCreateRequestDto
     */
    void createAccumulationRatePolicy(AccumulationRatePolicyCreateRequestDto requestDto);
}
