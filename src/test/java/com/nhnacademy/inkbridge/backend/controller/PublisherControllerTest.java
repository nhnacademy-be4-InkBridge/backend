package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: PublisherControllerTest.
 *
 * @author choijaehun
 * @version 2024/03/20
 */

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
        mockMvc.perform(post("/api/admin/publisher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        verify(publisherService, times(1)).createPublisher(request);

    }
}