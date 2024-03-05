package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyTypeService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: PointPolicyTypeControllerTest.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
@WebMvcTest(PointPolicyTypeController.class)
class PointPolicyTypeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointPolicyTypeService pointPolicyTypeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("포인트 정책 유형 전체 조회")
    void testGetPointPolicyTypes() throws Exception {
        PointPolicyTypeReadResponseDto responseDto = new PointPolicyTypeReadResponseDto(1,
            "REGISTER");

        List<PointPolicyTypeReadResponseDto> list = List.of(responseDto);

        given(pointPolicyTypeService.getPointPolicyTypes()).willReturn(list);

        mockMvc.perform(get("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].pointPolicyTypeId", equalTo(1)))
            .andExpect(jsonPath("$[0].policyType", equalTo("REGISTER")));
    }

    @Test
    @DisplayName("포인트 정책 유형 생성 - 유효성 검사 실패")
    void testCreatePointPolicyType_validation_failed() throws Exception {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("");

        mockMvc.perform(post("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class));
    }

    @Test
    @DisplayName("포인트 정책 유형 생성 - 존재하는 포인트 정책 유형")
    void testCreatePointPolicyType_duplicate() throws Exception {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("REGISTER");
        requestDto.setAccumulatePoint(1000L);

        doThrow(new AlreadyExistException(
            PointPolicyMessageEnum.POINT_POLICY_TYPE_ALREADY_EXIST.name()))
            .when(pointPolicyTypeService).createPointPolicyType(any());

        mockMvc.perform(post("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isConflict())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(AlreadyExistException.class));
    }

    @Test
    @DisplayName("포인트 정책 유형 생성 - 성공")
    void testCreatePointPolicyType_success() throws Exception {
        PointPolicyTypeCreateRequestDto requestDto = new PointPolicyTypeCreateRequestDto();
        requestDto.setPolicyType("REGISTER");
        requestDto.setAccumulatePoint(1000L);

        mockMvc.perform(post("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 유효성 검사 실패")
    void testUpdatePointPolicyType_validation_failed() throws Exception {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("");

        mockMvc.perform(put("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(ValidationException.class));
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 존재하지 않는 포인트 정책 유형")
    void testUpdatePointPolicyType_not_found() throws Exception {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("REVIEW");

        doThrow(new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.name()))
            .when(pointPolicyTypeService).updatePointPolicyType(any());

        mockMvc.perform(put("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class));
    }

    @Test
    @DisplayName("포인트 정책 유형 수정 - 성공")
    void testUpdatePointPolicyType_success() throws Exception {
        PointPolicyTypeUpdateRequestDto requestDto = new PointPolicyTypeUpdateRequestDto();
        requestDto.setPointPolicyTypeId(1);
        requestDto.setPolicyType("REVIEW");

        mockMvc.perform(put("/api/point-policy-types")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("포인트 정책 유형 삭제 - 존재하지 않는 포인트 정책 유형")
    void testDeletePointPolicyType_not_found() throws Exception {
        doThrow(new NotFoundException(PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.name()))
            .when(pointPolicyTypeService).deletePointPolicyTypeById(anyInt());

        mockMvc.perform(delete("/api/point-policy-types/{pointPolicyTypeId}", 1))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class));
    }

    @Test
    @DisplayName("포인트 정책 유형 삭제 - 성공")
    void testDeletePointPolicyType_success() throws Exception {
        mockMvc.perform(delete("/api/point-policy-types/{pointPolicyTypeId}", 1))
            .andExpect(status().isOk());
    }
}