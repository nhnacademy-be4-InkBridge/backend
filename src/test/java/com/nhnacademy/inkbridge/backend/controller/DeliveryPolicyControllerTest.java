package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.DeliveryPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: DeliveryPolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@WebMvcTest(DeliveryPolicyController.class)
class DeliveryPolicyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeliveryPolicyService deliveryPolicyService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    @DisplayName("배송비 정책 전체 조회 테스트")
    void testGetDeliveryPolicies() throws Exception {
        DeliveryPolicyReadResponseDto responseDto = new DeliveryPolicyReadResponseDto(1L, 1000L,
            LocalDate.of(2024, 1, 1), 50000L);

        given(deliveryPolicyService.getDeliveryPolicies()).willReturn(List.of(responseDto));

        mockMvc.perform(get("/api/delivery-policies")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].deliveryPolicyId", equalTo(1)))
            .andExpect(jsonPath("$[0].deliveryPrice", equalTo(1000)))
            .andExpect(jsonPath("$[0].createdAt", equalTo("2024-01-01")));

        verify(deliveryPolicyService, times(1)).getDeliveryPolicies();
    }

    @Test
    @WithMockUser
    @DisplayName("배송비 정책 단일 조회 - 존재하지 않는 배송비 id")
    void testGetDeliveryPolicyById_not_found() throws Exception {
        doThrow(
            new NotFoundException(DeliveryPolicyMessageEnum.DELIVERY_POLICY_NOT_FOUND.name())).when(
            deliveryPolicyService).getDeliveryPolicy(1L);

        mockMvc.perform(get("/api/delivery-policies/{deliveryPolicyId}", 1L)
                .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class));

        verify(deliveryPolicyService, times(1)).getDeliveryPolicy(1L);
    }

    @Test
    @WithMockUser
    @DisplayName("배송비 정책 단일 조회 - 성공")
    void testGetDeliveryPolicyById_success() throws Exception {
        DeliveryPolicyReadResponseDto responseDto = new DeliveryPolicyReadResponseDto(1L, 1000L,
            LocalDate.of(2024, 1, 1), 50000L);

        given(deliveryPolicyService.getDeliveryPolicy(1L)).willReturn(responseDto);

        mockMvc.perform(get("/api/delivery-policies/{deliveryPolicyId}", 1L)
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.deliveryPolicyId", equalTo(1)))
            .andExpect(jsonPath("$.deliveryPrice", equalTo(1000)))
            .andExpect(jsonPath("$.createdAt", equalTo("2024-01-01")));

        verify(deliveryPolicyService, times(1)).getDeliveryPolicy(1L);
    }

    @Test
    @WithMockUser
    @DisplayName("현재 적용 배송비 정책 조회")
    void testGetCurrentDeliveryPolicy() throws Exception {
        DeliveryPolicyReadResponseDto responseDto = new DeliveryPolicyReadResponseDto(1L, 1000L,
            LocalDate.of(2024, 1, 1), 50000L);

        given(deliveryPolicyService.getCurrentDeliveryPolicy()).willReturn(responseDto);

        mockMvc.perform(get("/api/delivery-policies/current", 1L)
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.deliveryPolicyId", equalTo(1)))
            .andExpect(jsonPath("$.deliveryPrice", equalTo(1000)))
            .andExpect(jsonPath("$.createdAt", equalTo("2024-01-01")));

        verify(deliveryPolicyService, times(1)).getCurrentDeliveryPolicy();
    }

    @Test
    @WithMockUser
    @DisplayName("배송비 정책 생성 - 유효성 검사 실패")
    void testCreateDeliveryPolicy_valid_failed() throws Exception {
        DeliveryPolicyCreateRequestDto requestDto = new DeliveryPolicyCreateRequestDto();
        requestDto.setDeliveryPrice(-1000L);

        mockMvc.perform(post("/api/delivery-policies")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class));
    }

    @Test
    @WithMockUser
    @DisplayName("배송비 정책 생성 - 성공")
    void testCreateDeliveryPolicy_success() throws Exception {
        DeliveryPolicyCreateRequestDto requestDto = new DeliveryPolicyCreateRequestDto();
        requestDto.setDeliveryPrice(1000L);

        mockMvc.perform(post("/api/delivery-policies")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated());

        verify(deliveryPolicyService, times(1)).createDeliveryPolicy(any());
    }
}