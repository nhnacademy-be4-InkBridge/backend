package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: PointPolicyServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
@ExtendWith(MockitoExtension.class)
class PointPolicyServiceImplTest {

    @InjectMocks
    PointPolicyServiceImpl pointPolicyService;

    @Mock
    PointPolicyRepository pointPolicyRepository;

    @Mock
    PointPolicyTypeRepository pointPolicyTypeRepository;

    @Test
    @DisplayName("포인트 정책 전체 조회 테스트")
    void testGetPointPolicies() {
        PointPolicyReadResponseDto responseDto = new PointPolicyReadResponseDto(1L, "REGISTER",
            1000L, LocalDate.of(2024, 1, 1));

        List<PointPolicyReadResponseDto> list = List.of(responseDto);

        given(pointPolicyRepository.findAllPointPolicyBy()).willReturn(list);

        List<PointPolicyReadResponseDto> actual = pointPolicyService.getPointPolicies();

        assertEquals(list, actual);

        verify(pointPolicyRepository, times(1)).findAllPointPolicyBy();
    }

    @Test
    @DisplayName("포인트 정책 생성 - 존재하지 않는 포인트 정책 유형 테스트")
    void testCreatePointPolicy_not_found_type() {
        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setAccumulatePoint(1000L);

        given(pointPolicyTypeRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> pointPolicyService.createPointPolicy(requestDto));

        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("포인트 정책 생성 - 성공")
    void testCreatePointPolicy_success() {
        PointPolicyType pointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(1)
            .policyType("REGISTER").build();

        given(pointPolicyTypeRepository.findById(1)).willReturn(Optional.of(pointPolicyType));

        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setAccumulatePoint(1000L);

        pointPolicyService.createPointPolicy(requestDto);

        verify(pointPolicyTypeRepository, times(1)).findById(1);
        verify(pointPolicyRepository, times(1)).save(any());
    }
}