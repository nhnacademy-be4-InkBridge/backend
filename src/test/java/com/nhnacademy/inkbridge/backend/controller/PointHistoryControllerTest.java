package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: PointHistoryControllerTest.
 *
 * @author jeongbyeonghun
 * @version 3/24/24
 */


@AutoConfigureRestDocs
@WebMvcTest(PointHistoryController.class)
class PointHistoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PointHistoryService pointHistoryService;

    private static List<PointHistoryReadResponseDto> testList;
    private static String reason;
    private static Long point;
    private static LocalDateTime accruedAt;

    @BeforeAll
    static void setUp() {
        testList = new ArrayList<>();
        reason = "test";
        point = 100L;
        accruedAt = LocalDateTime.now();
        testList.add(new PointHistoryReadResponseDto(reason, point, accruedAt));
    }

    @Test
    void getTestHistory() throws Exception {
        when(pointHistoryService.getPointHistory(anyLong())).thenReturn(testList);

        mvc.perform(get("/api/mypage/pointHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization-Id", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].reason", equalTo(reason)))
            .andExpect(jsonPath("$[0].point", equalTo(point.intValue())))
            .andExpect(jsonPath("$[0].accruedAt",
                equalTo(accruedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
            .andDo(document("docs"));

    }
}