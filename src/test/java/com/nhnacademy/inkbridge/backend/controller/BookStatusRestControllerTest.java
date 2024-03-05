package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookStatusService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: BookStatusRestControllerTest.
 *
 * @author minm063
 * @version 2024/02/28
 */
@AutoConfigureRestDocs
@WebMvcTest(BookStatusRestController.class)
class BookStatusRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookStatusService bookStatusService;

    @Test
    void whenGetBookStatuses_thenReturnDtoList() throws Exception {
        BookStatusReadResponseDto bookStatusReadResponseDto = BookStatusReadResponseDto.builder()
            .statusId(1L)
            .statusName("test")
            .build();

        when(bookStatusService.getStatuses()).thenReturn(List.of(bookStatusReadResponseDto));

        mockMvc.perform(get("/api/admin/statuses"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].statusId", equalTo(1)))
            .andExpect(jsonPath("$[0].statusName", equalTo("test")));
    }
}