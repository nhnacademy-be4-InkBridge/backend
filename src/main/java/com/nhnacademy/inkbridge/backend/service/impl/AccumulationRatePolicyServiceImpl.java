package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.AccumulationRatePolicy;
import com.nhnacademy.inkbridge.backend.enums.AccumulationRatePolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.AccumulationRatePolicyRepository;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: AccumulationRatePolicyServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@Service
@RequiredArgsConstructor
public class AccumulationRatePolicyServiceImpl implements AccumulationRatePolicyService {

    private final AccumulationRatePolicyRepository accumulationRatePolicyRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccumulationRatePolicyReadResponseDto> getAccumulationRatePolicies() {
        return accumulationRatePolicyRepository.findAllAccumulationRatePolicies();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public AccumulationRatePolicyReadResponseDto getAccumulationRatePolicy(
        Long accumulationRatePolicyId) {

        if (!accumulationRatePolicyRepository.existsById(accumulationRatePolicyId)) {
            throw new NotFoundException(
                AccumulationRatePolicyMessageEnum.ACCUMULATION_RATE_POLICY_NOT_FOUND.getMessage());
        }

        return accumulationRatePolicyRepository.findAccumulationRatePolicy(
            accumulationRatePolicyId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public AccumulationRatePolicyReadResponseDto getCurrentAccumulationRatePolicy() {
        return accumulationRatePolicyRepository.findCurrentAccumulationRatePolicy();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createAccumulationRatePolicy(AccumulationRatePolicyCreateRequestDto requestDto) {
        AccumulationRatePolicy accumulationRatePolicy = AccumulationRatePolicy.builder()
            .accumulationRate(requestDto.getAccumulationRate())
            .createdAt(LocalDate.now())
            .build();

        accumulationRatePolicyRepository.save(accumulationRatePolicy);
    }
}