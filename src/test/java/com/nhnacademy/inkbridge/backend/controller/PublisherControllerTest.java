package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: PublisherControllerTest.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
@AutoConfigureRestDocs
@WebMvcTest(PublisherController.class)
class PublisherControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    PublisherService publisherService;

    @Test
    @DisplayName("Publisher 데이터 생성 테스트 - 성공")
    void when_createPublisher_expect_success() throws Exception {
        PublisherCreateRequestDto request = new PublisherCreateRequestDto();
        ReflectionTestUtils.setField(request, "publisherName", "nhn 문고");
        mockMvc.perform(post("/api/admin/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(document("publisher/create",
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름"))));
        verify(publisherService, times(1)).createPublisher(request);
    }

    @Test
    @DisplayName("Publisher 데이터 생성 테스트 - 글자수 유효성 검사: 0글자")
    void when_createPublisher_expect_fail_1() throws Exception {
        PublisherCreateRequestDto request = new PublisherCreateRequestDto();
        ReflectionTestUtils.setField(request, "publisherName", "");
        mockMvc.perform(post("/api/admin/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("publisher/create-validation",
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름"))));

        verify(publisherService, times(0)).createPublisher(request);
    }

    @Test
    @DisplayName("Publisher 데이터 생성 테스트 - 글자수 유효성 검사: null")
    void when_createPublisher_expect_fail_2() throws Exception {
        PublisherCreateRequestDto request = new PublisherCreateRequestDto();
        mockMvc.perform(post("/api/admin/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("publisher/create-validation",
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름"))));

        verify(publisherService, times(0)).createPublisher(request);
    }

    @Test
    @DisplayName("Publisher 데이터 생성 테스트 - 글자수 유효성 검사: 30글자 초과")
    void when_createPublisher_expect_fail_3() throws Exception {
        PublisherCreateRequestDto request = new PublisherCreateRequestDto();
        ReflectionTestUtils.setField(request, "publisherName", "1234567890123456789012345678901");
        mockMvc.perform(post("/api/admin/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("publisher/create-validation",
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름"))));

        verify(publisherService, times(0)).createPublisher(request);
    }

    @Test
    @DisplayName("Publisher 데이터 조회 테스트 - 성공")
    void when_readPublishers_expect_success() throws Exception {
        PublisherReadResponseDto dto1 = new PublisherReadResponseDto(1L, "nhn 문고");
        PublisherReadResponseDto dto2 = new PublisherReadResponseDto(2L, "조선 문고");
        Page<PublisherReadResponseDto> response = new PageImpl<>(List.of(dto1, dto2));
        when(publisherService.readPublishers(any())).thenReturn(response);

        mockMvc.perform(get("/api/admin/publishers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].publisherId").value(1L))
            .andDo(document("publisher/read",
                relaxedResponseFields(
                    fieldWithPath("content[]").description("Array of publishers"),
                    fieldWithPath("content[].publisherId").description("출판사 아이디").type(
                        JsonFieldType.NUMBER),
                    fieldWithPath("content[].publisherName").description("출판사 이름").type(JsonFieldType.STRING))
            ));
    }

    @Test
    @DisplayName("Publisher 데이터 수정 테스트 - 성공")
    void when_updatePublisher_expect_success() throws Exception {
        Long publisherId = 1L;
        String publisherName = "수정된 출판사";
        PublisherUpdateRequestDto request = new PublisherUpdateRequestDto();

        ReflectionTestUtils.setField(request, "publisherName", publisherName);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/admin/publishers/{publisherId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(document("publisher/update",
                pathParameters(
                    parameterWithName("publisherId").description("출판사 아이디")
                ),
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름").type(JsonFieldType.STRING)
                )
            ));

        verify(publisherService, times(1)).updatePublisher(publisherId, request);
    }

    @Test
    @DisplayName("Publisher 데이터 수정 테스트 - 글자수 유효성 검사: 0글자")
    void when_updatePublisher_expect_fail_1() throws Exception {
        Long publisherId = 1L;
        PublisherUpdateRequestDto request = new PublisherUpdateRequestDto();
        ReflectionTestUtils.setField(request, "publisherName", "");
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/admin/publishers/{publisherId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("publisher/update-validation",
                pathParameters(
                    parameterWithName("publisherId").description("출판사 아이디")
                ),
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름").type(JsonFieldType.STRING)
                )
            ));

        verify(publisherService, times(0)).updatePublisher(publisherId, request);
    }

    @Test
    @DisplayName("Publisher 데이터 수정 테스트 - 글자수 유효성 검사: 31글자")
    void when_updatePublisher_expect_fail_2() throws Exception {
        Long publisherId = 1L;
        PublisherUpdateRequestDto request = new PublisherUpdateRequestDto();
        ReflectionTestUtils.setField(request, "publisherName", "1234567890123456789012345678901");
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/admin/publishers/{publisherId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("publisher/update-validation",
                pathParameters(
                    parameterWithName("publisherId").description("출판사 아이디")
                ),
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름").type(JsonFieldType.STRING)
                )
            ));

        verify(publisherService, times(0)).updatePublisher(publisherId, request);
    }

    @Test
    @DisplayName("Publisher 데이터 수정 테스트 - 글자수 유효성 검사: null")
    void when_updatePublisher_expect_fail_3() throws Exception {
        Long publisherId = 1L;
        PublisherUpdateRequestDto request = new PublisherUpdateRequestDto();
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/admin/publishers/{publisherId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("publisher/update-validation",
                pathParameters(
                    parameterWithName("publisherId").description("출판사 아이디")
                ),
                requestFields(
                    fieldWithPath("publisherName").description("출판사 이름").type(JsonFieldType.NULL)
                )
            ));

        verify(publisherService, times(0)).updatePublisher(publisherId, request);
    }
}