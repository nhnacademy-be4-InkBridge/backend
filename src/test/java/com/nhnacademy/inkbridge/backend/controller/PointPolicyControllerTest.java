package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: PointPolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
@WebMvcTest(PointPolicyController.class)
class PointPolicyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointPolicyService pointPolicyService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("포인트 정책 전체 조회 테스트")
    void testGetPointPolicies() throws Exception {
        PointPolicyReadResponseDto responseDto = new PointPolicyReadResponseDto();
        responseDto.setPointPolicyId(1L);
        responseDto.setPolicyType("REGISTER");
        responseDto.setAccumulatePoint(1000L);
        responseDto.setCreatedAt(LocalDate.of(2024, 1, 1));

        List<PointPolicyReadResponseDto> list = new ArrayList<>();
        list.add(responseDto);

        given(pointPolicyService.getPointPolicies()).willReturn(list);

        mockMvc.perform(get("/pointpolicy"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("포인트 정책 생성 - 유효성 검사 실패 테스트")
    void testCreatePointPolicy_validation_failed() throws Exception {
        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setAccumulatePoint(-100L);
        requestDto.setPointPolicyTypeId(1);

        mockMvc.perform(post("/pointpolicy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(exception -> assertThat(exception.getResolvedException())
                        .isInstanceOf(ValidationException.class));
    }

    @Test
    @DisplayName("포인트 정책 생성 - 포인트 정책 유형이 존재하지 않는 경우 테스트")
    void testCreatePointPolicy_not_found_policy_type() throws Exception {
        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setAccumulatePoint(100L);
        requestDto.setPointPolicyTypeId(1);

        doThrow(new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.name()))
                .when(pointPolicyService).createPointPolicy(any());

        mockMvc.perform(post("/pointpolicy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(exception -> assertThat(exception.getResolvedException())
                        .isInstanceOf(NotFoundException.class));
    }

    @Test
    @DisplayName("포인트 정책 생성 - 성공")
    void testCreatePointPolicy_success() throws Exception {
        PointPolicyCreateRequestDto requestDto = new PointPolicyCreateRequestDto();
        requestDto.setAccumulatePoint(100L);
        requestDto.setPointPolicyTypeId(1);

        mockMvc.perform(post("/pointpolicy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }


}