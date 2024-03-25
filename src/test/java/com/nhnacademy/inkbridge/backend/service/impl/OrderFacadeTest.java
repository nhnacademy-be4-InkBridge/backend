package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.facade.OrderFacade;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: OrderFacadeTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@ExtendWith(MockitoExtension.class)
class OrderFacadeTest {

    @InjectMocks
    OrderFacade orderFacade;

    @Mock
    BookOrderService bookOrderService;

    @Mock
    BookOrderDetailService bookOrderDetailService;

    @Test
    @DisplayName("주문 생성 - 성공")
    void testCreateOrder_success() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);

        OrderCreateResponseDto result = orderFacade.createOrder(requestDto);

        assertEquals(responseDto, result);

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 멤버 번호에 맞는 멤버가 없는 경우")
    void testCreateOrder_member_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());

        given(bookOrderService.createBookOrder(any())).willThrow(new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
    }

    @Test
    @DisplayName("주문 생성 - 도서 번호에 맞는 도서가 없는 경우")
    void testCreateOrder_book_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 쿠폰 번호에 맞는 쿠폰이 없는 경우")
    void testCreateOrder_coupon_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(CouponMessageEnum.COUPON_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 주문 상태 번호에 맞는 주문 상태가 없는 경우")
    void testCreateOrder_book_order_status_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(OrderMessageEnum.ORDER_STATUS_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 생성 - 포장지 번호에 맞는 포장지가 없는 경우")
    void testCreateOrder_wrapping_not_found() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookOrderList",
            List.of(new BookOrderDetailCreateRequestDto()));
        ReflectionTestUtils.setField(requestDto, "bookOrder", new BookOrderCreateRequestDto());
        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(1L, "orderCode");

        given(bookOrderService.createBookOrder(any())).willReturn(responseDto);
        doThrow(new NotFoundException(OrderMessageEnum.WRAPPING_NOT_FOUND.getMessage())).when(
            bookOrderDetailService).createBookOrderDetail(any(), anyList());

        assertThrows(NotFoundException.class, () -> orderFacade.createOrder(requestDto));

        verify(bookOrderService, times(1)).createBookOrder(any());
        verify(bookOrderDetailService, times(1)).createBookOrderDetail(any(), anyList());
    }

    @Test
    @DisplayName("주문 결제 정보 조회 - 주문 번호에 맞는 주문이 없는 경우")
    void testGetOrderPaymentInfo_not_found() {
        given(bookOrderService.getOrderPaymentInfoByOrderCode("orderId")).willThrow(
            new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        assertThrows(NotFoundException.class, () -> orderFacade.getOrderPaymentInfo("orderId"));

        verify(bookOrderService, times(1)).getOrderPaymentInfoByOrderCode("orderId");
    }
    
    @Test
    @DisplayName("주문 결제 정보 조회 - 성공")
    void testGetOrderPaymentInfo_success() {
        OrderPayInfoReadResponseDto responseDto = new OrderPayInfoReadResponseDto("orderId",
            "orderName", 10000L);

        given(bookOrderService.getOrderPaymentInfoByOrderCode("orderId")).willReturn(responseDto);

        OrderPayInfoReadResponseDto result = orderFacade.getOrderPaymentInfo("orderId");

        verify(bookOrderService, times(1)).getOrderPaymentInfoByOrderCode("orderId");
    }
}