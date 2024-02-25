package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
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
 * class: AccumulationRatePolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@WebMvcTest(AccumulationRatePolicyController.class)
class AccumulationRatePolicyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccumulationRatePolicyService accumulationRatePolicyService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    @DisplayName("기본 적립율 전체 내역 조회 테스트")
    void testGetAccumulationRatePolicies() throws Exception {
        AccumulationRatePolicyReadResponseDto responseDto =
            new AccumulationRatePolicyReadResponseDto(1L, 1, LocalDate.of(2024, 1, 1));

        given(accumulationRatePolicyService.getAccumulationRatePolicies()).willReturn(
            List.of(responseDto));

        mockMvc.perform(get("/api/accumulation-rate-policies")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].accumulationRatePolicyId").value(1L))
            .andExpect(jsonPath("$[0].accumulationRate", equalTo(1)))
            .andExpect(jsonPath("$[0].createdAt", equalTo("2024-01-01")));

        verify(accumulationRatePolicyService, times(1)).getAccumulationRatePolicies();
    }

    @Test
    @WithMockUser
    @DisplayName("적립율 정책 id로 내역 조회 - 존재하지 않는 적립율 정책")
    void testGetAccumulationRatePolicy_not_found() throws Exception {
        given(accumulationRatePolicyService.getAccumulationRatePolicy(1L)).willThrow(
            NotFoundException.class);

        mockMvc.perform(get("/api/accumulation-rate-policies/{accumulationRatePolicy}", 1L)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class));

        verify(accumulationRatePolicyService, times(1)).getAccumulationRatePolicy(1L);
    }

    @Test
    @WithMockUser
    @DisplayName("적립율 정책 id로 내역 조회 - 조회 성공")
    void testGetAccumulationRatePolicy_success() throws Exception {
        AccumulationRatePolicyReadResponseDto responseDto =
            new AccumulationRatePolicyReadResponseDto(1L, 1, LocalDate.of(2024, 1, 1));

        given(accumulationRatePolicyService.getAccumulationRatePolicy(1L)).willReturn(responseDto);

        mockMvc.perform(get("/api/accumulation-rate-policies/{accumulationRatePolicy}", 1L)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accumulationRatePolicyId").value(1L))
            .andExpect(jsonPath("$.accumulationRate", equalTo(1)))
            .andExpect(jsonPath("$.createdAt", equalTo("2024-01-01")));

        verify(accumulationRatePolicyService, times(1)).getAccumulationRatePolicy(1L);
    }

    @Test
    @WithMockUser
    @DisplayName("현재 적용중인 적립율 정책 조회")
    void testGetCurrentAccumulationRatePolicy() throws Exception {
        AccumulationRatePolicyReadResponseDto responseDto =
            new AccumulationRatePolicyReadResponseDto(1L, 1, LocalDate.of(2024, 1, 1));

        given(accumulationRatePolicyService.getCurrentAccumulationRatePolicy()).willReturn(
            responseDto);

        mockMvc.perform(get("/api/accumulation-rate-policies/current", 1L)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accumulationRatePolicyId").value(1L))
            .andExpect(jsonPath("$.accumulationRate", equalTo(1)))
            .andExpect(jsonPath("$.createdAt", equalTo("2024-01-01")));

        verify(accumulationRatePolicyService, times(1)).getCurrentAccumulationRatePolicy();
    }

    @Test
    @WithMockUser
    @DisplayName("적립율 정책 생성 - 유효성 검사 실패")
    void testCreateAccumulationRatePolicy_valid_failed() throws Exception {
        AccumulationRatePolicyCreateRequestDto requestDto = new AccumulationRatePolicyCreateRequestDto();
        requestDto.setAccumulationRate(-5);

        mockMvc.perform(post("/api/accumulation-rate-policies")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class));
    }

    @Test
    @WithMockUser
    @DisplayName("적립율 정책 생성 - 생성 성공")
    void testCreateAccumulationRatePolicy_success() throws Exception {
        AccumulationRatePolicyCreateRequestDto requestDto = new AccumulationRatePolicyCreateRequestDto();
        requestDto.setAccumulationRate(5);

        mockMvc.perform(post("/api/accumulation-rate-policies")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated());

        verify(accumulationRatePolicyService, times(1)).createAccumulationRatePolicy(any());
    }
}