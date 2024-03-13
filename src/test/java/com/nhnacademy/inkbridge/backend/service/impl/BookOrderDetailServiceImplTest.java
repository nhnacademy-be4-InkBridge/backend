package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.BookOrderStatus;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.Wrapping;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.WrappingRepository;
import java.util.List;
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
 * class: BookOrderDetailServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@ExtendWith(MockitoExtension.class)
class BookOrderDetailServiceImplTest {

    @InjectMocks
    BookOrderDetailServiceImpl bookOrderDetailService;
    @Mock
    BookOrderRepository bookOrderRepository;
    @Mock
    BookOrderDetailRepository bookOrderDetailRepository;
    @Mock
    BookOrderStatusRepository bookOrderStatusRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    MemberCouponRepository memberCouponRepository;
    @Mock
    WrappingRepository wrappingRepository;

    BookOrderDetailCreateRequestDto requestDto;


    @BeforeEach
    void setup() {
        requestDto = new BookOrderDetailCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "bookId", 1L);
        ReflectionTestUtils.setField(requestDto, "price", 10000L);
        ReflectionTestUtils.setField(requestDto, "amount", 3);
        ReflectionTestUtils.setField(requestDto, "wrappingId", null);
        ReflectionTestUtils.setField(requestDto, "couponId", "");
        ReflectionTestUtils.setField(requestDto, "wrappingPrice", 0L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 성공 - 포장지, 쿠폰 선택 안한 경우")
    void testCreateOrderDetail_success_no_check_wrapping_coupon() {
        Book book = Book.builder().build();
        BookOrderStatus status = BookOrderStatus.builder().build();
        BookOrder bookOrder = BookOrder.builder().build();

        given(bookOrderRepository.findById(any())).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.of(status));

        bookOrderDetailService.createBookOrderDetail("orderId", List.of(requestDto));

        verify(bookOrderRepository, times(1)).findById(any());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 성공 - 포장지, 쿠폰 선택한 경우")
    void testCrateOrderDetail_success_check_wrapping_coupon() {
        ReflectionTestUtils.setField(requestDto, "wrappingId", 1L);
        ReflectionTestUtils.setField(requestDto, "couponId", "couponId");
        ReflectionTestUtils.setField(requestDto, "wrappingPrice", 3000L);

        Book book = Book.builder().build();
        BookOrderStatus status = BookOrderStatus.builder().build();
        BookOrder bookOrder = BookOrder.builder().build();
        MemberCoupon coupon = MemberCoupon.builder().build();
        Wrapping wrapping = Wrapping.builder().build();

        given(bookOrderRepository.findById(any())).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById("couponId")).willReturn(Optional.of(coupon));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.of(status));
        given(wrappingRepository.findById(1L)).willReturn(Optional.of(wrapping));

        bookOrderDetailService.createBookOrderDetail("orderId", List.of(requestDto));

        verify(bookOrderRepository, times(1)).findById(any());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookOrderStatusRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById("couponId");
        verify(wrappingRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 주문이 없는 경우")
    void testCreateBookOrderDetail_order_not_found() {
        given(bookOrderRepository.findById("orderId")).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail("orderId", List.of(requestDto)));

        verify(bookOrderRepository, times(1)).findById("orderId");
    }

    @Test
    @DisplayName("주문 상세 생성 - 도서가 없는 경우")
    void testCreateBookOrderDetail_book_not_found() {
        BookOrder bookOrder = BookOrder.builder().build();

        given(bookOrderRepository.findById("orderId")).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail("orderId", List.of(requestDto)));

        verify(bookOrderRepository, times(1)).findById("orderId");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 쿠폰가 없는 경우")
    void testCreateBookOrderDetail_coupon_not_found() {
        ReflectionTestUtils.setField(requestDto, "couponId", "couponId");
        BookOrder bookOrder = BookOrder.builder().build();
        Book book = Book.builder().build();

        given(bookOrderRepository.findById("orderId")).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById("couponId")).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail("orderId", List.of(requestDto)));

        verify(bookOrderRepository, times(1)).findById("orderId");
        verify(bookRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById("couponId");
    }

    @Test
    @DisplayName("주문 상세 생성 - 주문 상태가 없는 경우")
    void testCreateBookOrderDetail_order_status_not_found() {
        ReflectionTestUtils.setField(requestDto, "couponId", "couponId");
        BookOrder bookOrder = BookOrder.builder().build();
        Book book = Book.builder().build();
        MemberCoupon coupon = MemberCoupon.builder().build();

        given(bookOrderRepository.findById("orderId")).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById("couponId")).willReturn(Optional.of(coupon));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail("orderId", List.of(requestDto)));

        verify(bookOrderRepository, times(1)).findById("orderId");
        verify(bookRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById("couponId");
        verify(bookOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 상세 생성 - 포장지가 없는 경우")
    void testCreateBookOrderDetail_wrapping_not_found() {
        ReflectionTestUtils.setField(requestDto, "couponId", "couponId");
        ReflectionTestUtils.setField(requestDto, "wrappingId", 1L);
        BookOrder bookOrder = BookOrder.builder().build();
        Book book = Book.builder().build();
        MemberCoupon coupon = MemberCoupon.builder().build();
        BookOrderStatus status = BookOrderStatus.builder().build();

        given(bookOrderRepository.findById("orderId")).willReturn(Optional.of(bookOrder));
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(memberCouponRepository.findById("couponId")).willReturn(Optional.of(coupon));
        given(bookOrderStatusRepository.findById(1L)).willReturn(Optional.of(status));
        given(wrappingRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookOrderDetailService.createBookOrderDetail("orderId", List.of(requestDto)));

        verify(bookOrderRepository, times(1)).findById("orderId");
        verify(bookRepository, times(1)).findById(1L);
        verify(memberCouponRepository, times(1)).findById("couponId");
        verify(bookOrderStatusRepository, times(1)).findById(1L);
        verify(wrappingRepository, times(1)).findById(1L);
    }

}