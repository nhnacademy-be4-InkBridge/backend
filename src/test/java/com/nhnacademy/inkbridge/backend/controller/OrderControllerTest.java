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
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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

    BookOrderDetailCreateRequestDto orderDetailCreateRequestDto;

    BookOrderCreateRequestDto orderCreateRequestDto;

    @BeforeEach
    void setup() {
        orderDetailCreateRequestDto = new BookOrderDetailCreateRequestDto();
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "bookId", 1L);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "price", 18000L);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "amount", 3);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "wrappingId", 1L);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "couponId", 1L);
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "wrappingPrice", 3000L);

        orderCreateRequestDto = new BookOrderCreateRequestDto();
        ReflectionTestUtils.setField(orderCreateRequestDto, "orderName", "orderName");
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverName", "receiverName");
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverPhoneNumber", "01011112222");
        ReflectionTestUtils.setField(orderCreateRequestDto, "zipCode", "00000");
        ReflectionTestUtils.setField(orderCreateRequestDto, "address", "address");
        ReflectionTestUtils.setField(orderCreateRequestDto, "detailAddress", "detailAddress");
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderName", "senderName");
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderPhoneNumber",
            "01033334444");
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
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문 이름 공백")
    void testCreateOrder_valid_failed_orderName_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "orderName", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/order-name/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문 이름 길이")
    void testCreateOrder_valid_failed_orderName_size() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "orderName",
            UUID.randomUUID().toString().concat(UUID.randomUUID().toString()));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/order-name/size",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 수취인 이름 길이")
    void testCreateOrder_valid_failed_receiverName_size() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverName", "TeamInkBridgeReceiverName");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/receiver-name/size",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 수취인 이름 공백")
    void testCreateOrder_valid_failed_receiverName_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverName", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/receiver-name/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 수취인 전화번호 패턴")
    void testCreateOrder_valid_failed_receiverPhoneNumber_pattern() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverPhoneNumber", "111122223333");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/receiver-phone-number/pattern",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 수취인 전화번호 공백")
    void testCreateOrder_valid_failed_receiverPhoneNumber_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "receiverPhoneNumber", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/receiver-phone-number/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 우편번호 공백")
    void testCreateOrder_valid_failed_zipCode_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "zipCode", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/zipCode/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 우편번호 패턴")
    void testCreateOrder_valid_failed_zipCode_pattern() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "zipCode", "1234567");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/zipCode/pattern",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주소 공백")
    void testCreateOrder_valid_failed_address_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "address", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/address/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 상세 주소 공백")
    void testCreateOrder_valid_failed_detailAddress_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "detailAddress", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/detail-address/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문인 이름 공백")
    void testCreateOrder_valid_failed_senderName_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderName", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/sender-name/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문인 이름 길이")
    void testCreateOrder_valid_failed_senderName_size() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderName", "TeamInkBridgeSenderName");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/sender-name/size",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문인 전화번호 패턴")
    void testCreateOrder_valid_failed_senderPhoneNumber_pattern() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderPhoneNumber", "111122223333");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/sender-phone-number/pattern",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문인 전화번호 공백")
    void testCreateOrder_valid_failed_senderPhoneNumber_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderPhoneNumber", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/sender-phone-number/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문인 이메일 공백")
    void testCreateOrder_valid_failed_senderEmail_blank() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderEmail", "");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/sender-email/blank",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문인 이메일 공백")
    void testCreateOrder_valid_failed_senderEmail_email() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "senderEmail", "inkbridge.store");

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/sender-email/email",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 배송 예정일 널 값")
    void testCreateOrder_valid_failed_deliveryDate_null() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "deliveryDate", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/delivery-date/null",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 포인트 음수")
    void testCreateOrder_valid_failed_point_negative() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "usePoint", -15L);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/point/negative",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 포인트 널")
    void testCreateOrder_valid_failed_point_null() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "usePoint", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/point/null",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문 금액 음수")
    void testCreateOrder_valid_failed_payAmount_negative() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "payAmount", -10000L);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/pay-amount/negative",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 주문 금액 널")
    void testCreateOrder_valid_failed_payAmount_null() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "payAmount", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/pay-amount/null",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 배송비 음수")
    void testCreateOrder_valid_failed_deliveryPrice_negative() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "deliveryPrice", -10000L);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/delivery-price/negative",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 배송비 널")
    void testCreateOrder_valid_failed_deliveryPrice_null() throws Exception {
        ReflectionTestUtils.setField(orderCreateRequestDto, "deliveryPrice", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/delivery-price/null",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 도서번호 널")
    void testCreateOrder_valid_failed_bookId_null() throws Exception {
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "bookId", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/book-id/null",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 도서 가격 널")
    void testCreateOrder_valid_failed_price_null() throws Exception {
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "price", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/price/null",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 도서 수량 널")
    void testCreateOrder_valid_failed_amount_null() throws Exception {
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "amount", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/amount/null",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 도서 수량 음수")
    void testCreateOrder_valid_failed_amount_negative() throws Exception {
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "amount", -1);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/amount/negative",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 포장비 음수")
    void testCreateOrder_valid_failed_wrapping_negative() throws Exception {
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "wrappingPrice", -1L);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/wrapping-price/negative",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("주문 정보 저장 요청 - 유효성 검사 실패 - 포장비 널")
    void testCreateOrder_valid_failed_wrapping_null() throws Exception {
        ReflectionTestUtils.setField(orderDetailCreateRequestDto, "wrappingPrice", null);

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(exception -> assertThat(exception.getResolvedException()).isInstanceOf(
                ValidationException.class))
            .andDo(document("order/order-post-422/wrapping-price/null",
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
        given(orderFacade.createOrder(any())).willReturn(new OrderCreateResponseDto(1L, "orderCode"));

        mockMvc.perform(post("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpectAll(
                status().isCreated(),
                jsonPath("orderCode", equalTo("orderCode"))
            )
            .andDo(document("order/order-post-201",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("orderId").description("주문 번호"),
                    fieldWithPath("orderCode").description("주문 코드")
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
                RestDocumentationRequestBuilders.get("/api/orders/{orderCode}/order-pays", "UUID")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.orderCode", equalTo("UUID")),
                jsonPath("$.orderName", equalTo("TestOrder")),
                jsonPath("$.amount").value(30000L)
            )
            .andDo(document("order/order-payment-path-get-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("orderCode").description("주문 코드")
                ),
                responseFields(
                    fieldWithPath("orderCode").description("주문 코드"),
                    fieldWithPath("orderName").description("주문 이름"),
                    fieldWithPath("amount").description("가격")
                )));
    }
}