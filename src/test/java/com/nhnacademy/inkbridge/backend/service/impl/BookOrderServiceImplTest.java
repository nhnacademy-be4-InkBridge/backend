package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: BookOrderServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@ExtendWith(MockitoExtension.class)
class BookOrderServiceImplTest {

    @InjectMocks
    BookOrderServiceImpl bookOrderService;

    @Mock
    BookOrderRepository bookOrderRepository;

    @Mock
    MemberRepository memberRepository;

    BookOrderCreateRequestDto requestDto;

    @BeforeEach
    void setup() {
        requestDto = new BookOrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "orderName", "orderName");
        ReflectionTestUtils.setField(requestDto, "receiverName", "receiverName");
        ReflectionTestUtils.setField(requestDto, "receiverPhoneNumber", "01011112222");
        ReflectionTestUtils.setField(requestDto, "zipCode", "00000");
        ReflectionTestUtils.setField(requestDto, "address", "address");
        ReflectionTestUtils.setField(requestDto, "detailAddress", "detailAddress");
        ReflectionTestUtils.setField(requestDto, "senderName", "senderName");
        ReflectionTestUtils.setField(requestDto, "senderPhoneNumber", "01033334444");
        ReflectionTestUtils.setField(requestDto, "senderEmail", "sender@inkbridge.store");
        ReflectionTestUtils.setField(requestDto, "deliveryDate", LocalDate.of(2024, 1, 1));
        ReflectionTestUtils.setField(requestDto, "usePoint", 0L);
        ReflectionTestUtils.setField(requestDto, "payAmount", 30000L);
        ReflectionTestUtils.setField(requestDto, "memberId", 1L);
        ReflectionTestUtils.setField(requestDto, "deliveryPrice", 3000L);

    }

    @Test
    @DisplayName("주문 생성 - 멤버 번호에 맞는 멤버가 없는 경우")
    void testCreateBookOrder_member_not_found() {
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookOrderService.createBookOrder(requestDto));
    }

    @Test
    @DisplayName("주문 생성 - 회원 성공")
    void testCreateBookOrder_member_success() {
        BookOrder bookOrder = BookOrder.builder()
            .orderCode("UUID")
            .build();
        Member member = Member.create().build();
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(bookOrderRepository.save(any())).willReturn(bookOrder);

        OrderCreateResponseDto result = bookOrderService.createBookOrder(requestDto);

        assertEquals(bookOrder.getOrderCode(), result.getOrderCode());

        verify(memberRepository, times(1)).findById(1L);
        verify(bookOrderRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("주문 생성 - 비회원 성공")
    void testCreateBookOrder_anonymous_success() {
        ReflectionTestUtils.setField(requestDto, "memberId", null);
        BookOrder bookOrder = BookOrder.builder()
            .orderCode("UUID")
            .build();

        given(bookOrderRepository.save(any())).willReturn(bookOrder);

        OrderCreateResponseDto result = bookOrderService.createBookOrder(requestDto);

        assertEquals(bookOrder.getOrderCode(), result.getOrderCode());

        verify(bookOrderRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문번호에 맞는 도서번호가 없는 경우")
    void testGetOrderPaymentInfoByOrderId_not_found() {
        given(bookOrderRepository.findOrderPayByOrderCode("orderId")).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderService.getOrderPaymentInfoByOrderCode("orderId"));

        verify(bookOrderRepository, times(1)).findOrderPayByOrderCode("orderId");
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 성공")
    void testGetOrderPaymentInfoByOrderId_success() {
        OrderPayInfoReadResponseDto responseDto = new OrderPayInfoReadResponseDto("orderId",
            "orderName", 10000L);

        given(bookOrderRepository.findOrderPayByOrderCode("orderId")).willReturn(
            Optional.of(responseDto));

        OrderPayInfoReadResponseDto result = bookOrderService.getOrderPaymentInfoByOrderCode(
            "orderId");

        assertAll(
            () -> assertEquals(responseDto.getOrderCode(), result.getOrderCode()),
            () -> assertEquals(responseDto.getOrderName(), result.getOrderName()),
            () -> assertEquals(responseDto.getAmount(), result.getAmount())
        );

        verify(bookOrderRepository, times(1)).findOrderPayByOrderCode("orderId");
    }
}