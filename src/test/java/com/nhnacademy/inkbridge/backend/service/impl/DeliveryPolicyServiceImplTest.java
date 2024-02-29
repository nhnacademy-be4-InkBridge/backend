package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.DeliveryPolicyRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: DeliveryPolicyServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@ExtendWith(MockitoExtension.class)
class DeliveryPolicyServiceImplTest {


    @InjectMocks
    DeliveryPolicyServiceImpl deliveryPolicyService;

    @Mock
    DeliveryPolicyRepository deliveryPolicyRepository;

    @Test
    @DisplayName("배송비 정책 전체 조회 메소드")
    void testGetDeliveryPolicies() {

        DeliveryPolicyReadResponseDto responseDto1 = ReflectionUtils.newInstance(
            DeliveryPolicyReadResponseDto.class,
            1L, 1000L, LocalDate.of(2024, 1, 1), 50000L);

        given(deliveryPolicyRepository.findAllDeliveryPolicyBy()).willReturn(List.of(responseDto1));

        List<DeliveryPolicyReadResponseDto> result = deliveryPolicyService.getDeliveryPolicies();

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(responseDto1, result.get(0))
        );

        verify(deliveryPolicyRepository, times(1)).findAllDeliveryPolicyBy();
    }

    @Test
    @DisplayName("배송비 정책 id로 조회 - 없는 배송비 정책")
    void testGetDeliveryPolicy_not_found() {
        given(deliveryPolicyRepository.existsById(1L)).willReturn(false);

        assertThrows(NotFoundException.class, () -> deliveryPolicyService.getDeliveryPolicy(1L));

        verify(deliveryPolicyRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("배송비 정책 id로 조회 - 조회 성공")
    void testGetDeliveryPolicy_found() {
        DeliveryPolicyReadResponseDto responseDto = new DeliveryPolicyReadResponseDto(1L, 1000L,
            LocalDate.of(2024, 1, 1), 50000L);

        given(deliveryPolicyRepository.existsById(1L)).willReturn(true);
        given(deliveryPolicyRepository.findDeliveryPolicyById(1L)).willReturn(responseDto);

        DeliveryPolicyReadResponseDto result = deliveryPolicyService.getDeliveryPolicy(1L);

        assertEquals(responseDto, result);

        verify(deliveryPolicyRepository, times(1)).existsById(1L);
        verify(deliveryPolicyRepository, times(1)).findDeliveryPolicyById(1L);
    }

    @Test
    @DisplayName("현재 배송비 정책 조회")
    void testGetCurrentDeliveryPolicy() {
        DeliveryPolicyReadResponseDto responseDto = new DeliveryPolicyReadResponseDto(1L, 1000L,
            LocalDate.of(2024, 1, 1), 50000L);

        given(deliveryPolicyRepository.findCurrentPolicy()).willReturn(responseDto);

        DeliveryPolicyReadResponseDto result = deliveryPolicyService.getCurrentDeliveryPolicy();

        assertEquals(responseDto, result);

        verify(deliveryPolicyRepository, times(1)).findCurrentPolicy();
    }

    @Test
    @DisplayName("배송비 정책 생성 테스트")
    void testCreateDeliveryPolicy() {
        DeliveryPolicyCreateRequestDto requestDto = new DeliveryPolicyCreateRequestDto();
        requestDto.setDeliveryPrice(1000L);

        deliveryPolicyService.createDeliveryPolicy(requestDto);

        verify(deliveryPolicyRepository, times(1)).save(any());
    }
}