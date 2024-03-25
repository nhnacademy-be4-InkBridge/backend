package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: CouponControllerTest.
 *
 * @author JBum
 * @version 2024/03/25
 */
@AutoConfigureRestDocs
@WebMvcTest(CouponController.class)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class CouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CouponService couponService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getCoupons() throws Exception {
        CouponReadResponseDto coupon1 = new CouponReadResponseDto("1", "쿠폰1", 10000L, 5000L, 0L,
            LocalDate.now(), LocalDate.now().plusDays(7), 7,
            CouponType.builder().couponTypeId(1).typeName("%").build(), true,
            CouponStatus.builder().couponStatusId(1).couponStatusName("정상").build());
        CouponReadResponseDto coupon2 = new CouponReadResponseDto("2", "쿠폰2", 20000L, 10000L, 0L,
            LocalDate.now(), LocalDate.now().plusDays(7), 7,
            CouponType.builder().couponTypeId(1).typeName("%").build(), true,
            CouponStatus.builder().couponStatusId(1).couponStatusName("정상").build());
        Page<CouponReadResponseDto> coupons = new PageImpl<>(Collections.singletonList(coupon1));

        given(couponService.getIssuableCoupons(any(Pageable.class))).willReturn(coupons);

        mockMvc.perform(get("/api/coupons").param("page", "0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].couponId").value("1"))
            .andExpect(jsonPath("$.content[0].couponName").value("쿠폰1"))
            .andExpect(jsonPath("$.content[0].minPrice").value(10000))
            .andExpect(jsonPath("$.content[0].discountPrice").value(5000))
            .andExpect(jsonPath("$.content[0].maxDiscountPrice").value(0))
            .andExpect(jsonPath("$.content[0].basicIssuedDate").exists())
            .andExpect(jsonPath("$.content[0].basicExpiredDate").exists())
            .andExpect(jsonPath("$.content[0].validity").value(7))
            .andExpect(jsonPath("$.content[0].couponTypeId").value(1))
            .andExpect(jsonPath("$.content[0].isBirth").value(true))
            .andExpect(jsonPath("$.content[0].couponStatusId").value(1))
            .andExpect(jsonPath("$.pageable").value("INSTANCE"))
            .andExpect(jsonPath("$.last").value(true))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.number").value(0))
            .andExpect(jsonPath("$.sort.empty").value(true))
            .andExpect(jsonPath("$.sort.sorted").value(false))
            .andExpect(jsonPath("$.sort.unsorted").value(true))
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.size").value(1))
            .andExpect(jsonPath("$.numberOfElements").value(1))
            .andExpect(jsonPath("$.empty").value(false))
            .andDo(document("coupons",
                preprocessRequest(prettyPrint()), // 요청 데이터 예쁘게 출력
                preprocessResponse(prettyPrint()), // 응답 데이터 예쁘게 출력
                responseFields(
                    fieldWithPath("content[].couponId").description("쿠폰 ID"),
                    fieldWithPath("content[].couponName").description("쿠폰 이름"),
                    fieldWithPath("content[].minPrice").description("최소 주문 가격"),
                    fieldWithPath("content[].discountPrice").description("할인 가격"),
                    fieldWithPath("content[].maxDiscountPrice").description("최대 할인 가격"),
                    fieldWithPath("content[].basicIssuedDate").description("쿠폰 발급일"),
                    fieldWithPath("content[].basicExpiredDate").description("쿠폰 만료일"),
                    fieldWithPath("content[].validity").description("쿠폰 유효 기간"),
                    fieldWithPath("content[].couponTypeId").description("쿠폰 유형 ID"),
                    fieldWithPath("content[].isBirth").description
                        ("생일 쿠폰 여부"),
                    fieldWithPath("content[].couponStatusId").description("쿠폰 상태 ID"),
                    fieldWithPath("pageable").description("페이지 정보"),
                    fieldWithPath("last").description("마지막 페이지 여부"),
                    fieldWithPath("totalPages").description("전체 페이지 수"),
                    fieldWithPath("totalElements").description("전체 요소 수"),
                    fieldWithPath("number").description("현재 페이지 번호 (0부터 시작)"),
                    fieldWithPath("size").description("페이지 크기"),
                    fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                    fieldWithPath("empty").description("비어 있는지 여부"),
                    fieldWithPath("sort.empty").description("정렬 여부: 비어 있음"),
                    fieldWithPath("sort.sorted").description("정렬 여부: 정렬됨"),
                    fieldWithPath("sort.unsorted").description("정렬 여부: 정렬되지 않음"),
                    fieldWithPath("first").description("처음")
                )
            ));
        verify(couponService, times(1)).getIssuableCoupons(PageRequest.of(0, 20));

    }
    
}