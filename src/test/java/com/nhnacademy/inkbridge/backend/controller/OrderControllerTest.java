package com.nhnacademy.inkbridge.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.impl.OrderFacade;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: OrderControllerTest.
 *
 * @author jangjaehun
 * @version 2024/03/12
 */
@AutoConfigureRestDocs
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderFacade orderFacade;

    ObjectMapper objectMapper = new ObjectMapper();

    OrderCreateRequestDto dto;

    @BeforeEach
    void setup() {
        BookOrderDetailCreateRequestDto orderDetailCreateRequestDto = new BookOrderDetailCreateRequestDto();
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "price", 18000L);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "amount", 3);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "wrappingId", 1L);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "couponId", "TestCoupon");
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "wrappingPrice", 3000L);

        BookOrderCreateRequestDto orderCreateRequestDto = new BookOrderCreateRequestDto();
        ReflectionTestUtils.setField(orderCreateRequestDto, "orderName", "orderName");
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverName", "receiverName");
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverPhoneNumber", "01011112222");
        ReflectionTestUtils.setField(orderCreateRequestDto, "zipCode", "00000");
        ReflectionTestUtils.setField(orderCreateRequestDto, "address", "address");
        ReflectionTestUtils.setField(orderCreateRequestDto, "detailAddress", "detailAddress");
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderName", "senderName");
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderPhoneNumber",
            "senderPhoneNumber");
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderEmail",
            "sender@inkbridge.store");
        ReflectionTestUtils.setField(orderCreateRequestDto, "deliveryDate",
            LocalDate.of(2024, 1, 1));
        ReflectionTestUtils.setField(orderCreateRequestDto, "usePoint", 0L);
        ReflectionTestUtils.setField(orderCreateRequestDto, "payAmount", 24000L);
        ReflectionTestUtils.setField(orderCreateRequestDto, "memberId", 1L);
        ReflectionTestUtils.setField(orderCreateRequestDto, "deliveryPrice", 3000L);

        dto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(dto, "bookOrderList", List.of(orderDetailCreateRequestDto));
        ReflectionTestUtils.setField(dto, "bookOrder", orderCreateRequestDto);

        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패")
    void testCreateOrder_valid_failed() throws Exception {

        BookOrderDetailCreateRequestDto detailCreateRequestDto = new BookOrderDetailCreateRequestDto();
        BookOrderCreateRequestDto orderCreateRequestDto = new BookOrderCreateRequestDto();

        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList", List.of(detailCreateRequestDto));
        ReflectionTestUtils.setField(requestDto, "bookOrder", orderCreateRequestDto);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 회원번호에 일치하는 회원이 없을 때")
    void testCreateOrder_member_not_found() throws Exception {
        given(orderFacade.createOrder(any())).willThrow(
            new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("order/order-post-member-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 도서번호에 일치하는 도서가 없을 때")
    void testCreateOrder_book_not_found() throws Exception {
        given(orderFacade.createOrder(any())).willThrow(
            new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage()));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("order/order-post-book-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 쿠폰번호에 일치하는 쿠폰이 없을 때")
    void testCreateOrder_coupon_not_found() throws Exception {
        given(orderFacade.createOrder(any())).willThrow(
            new NotFoundException(CouponMessageEnum.COUPON_NOT_FOUND.getMessage()));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("order/order-post-coupon-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 도서상태에 일치하는 상태가 없을 때")
    void testCreateOrder_book_status__not_found() throws Exception {
        given(orderFacade.createOrder(any())).willThrow(
            new NotFoundException(OrderMessageEnum.ORDER_STATUS_NOT_FOUND.getMessage()));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("order/order-post-order-status-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 쿠폰번호에 일치하는 쿠폰이 없을 때")
    void testCreateOrder_Coupon_not_found() throws Exception {
        given(orderFacade.createOrder(any())).willThrow(
            new NotFoundException(CouponMessageEnum.COUPON_NOT_FOUND.getMessage()));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("order/order-post-coupon-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 포장지번호에 일치하는 포장지가 없을 때")
    void testCreateOrder_wrapping_not_found() throws Exception {
        given(orderFacade.createOrder(any())).willThrow(
            new NotFoundException(OrderMessageEnum.WRAPPING_NOT_FOUND.getMessage()));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("order/order-post-wrapping-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 성공")
    void testCreateOrder_success() throws Exception {
        given(orderFacade.createOrder(any())).willReturn(new OrderCreateResponseDto("UUID"));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpectAll(
                status().isCreated(),
                jsonPath("orderId", equalTo("UUID"))
            )
            .andDo(document("order/order-post-201",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("orderId").description("주문 번호")
                )));
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문번호에 해당하는 주문이 없을 때")
    void testGetOrderPaymentInfo_not_found() throws Exception {
        given(orderFacade.getOrderPaymentInfo("UUID")).willThrow(
            new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/orders/{orderId}/order-pays", "UUID")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(exception -> assertThat(exception.getResolvedException())
                .isInstanceOf(NotFoundException.class))
            .andDo(document("order/order-payment-path-get-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 성공")
    void testGetOrderPaymentInfo_success() throws Exception {
        given(orderFacade.getOrderPaymentInfo("UUID")).willReturn(
            new OrderPayInfoReadResponseDto("UUID", "TestOrder", 30000L));

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/orders/{orderId}/order-pays", "UUID")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.orderId", equalTo("UUID")),
                jsonPath("$.orderName", equalTo("TestOrder")),
                jsonPath("$.amount").value(30000L)
            )
            .andDo(document("order/order-payment-path-get-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("orderId").description("주문 번호")
                ),
                responseFields(
                    fieldWithPath("orderId").description("주문 번호"),
                    fieldWithPath("orderName").description("주문 이름"),
                    fieldWithPath("amount").description("가격")
                )));
    }
}