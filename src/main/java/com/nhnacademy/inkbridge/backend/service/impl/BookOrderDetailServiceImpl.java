package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderDetailCreateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.BookOrderStatus;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.Wrapping;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.BookOrderStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.WrappingRepository;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: BookOrderDetailServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookOrderDetailServiceImpl implements BookOrderDetailService {

    private final BookOrderRepository bookOrderRepository;
    private final BookOrderDetailRepository bookOrderDetailRepository;
    private final BookOrderStatusRepository bookOrderStatusRepository;
    private final BookRepository bookRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final WrappingRepository wrappingRepository;

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @param requestDtoList 주문 상세 정보 목록
     */
    @Override
    public void createBookOrderDetail(Long orderId,
        List<BookOrderDetailCreateRequestDto> requestDtoList) {

        BookOrder bookOrder = bookOrderRepository.findById(orderId)
            .orElseThrow(
                () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        List<BookOrderDetail> bookOrderDetailList = requestDtoList.stream()
            .map(requestDto -> {
                Book book = bookRepository.findById(requestDto.getBookId())
                    .orElseThrow(
                        () -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage()));

                MemberCoupon coupon = Objects.nonNull(requestDto.getCouponId())
                    ? memberCouponRepository.findById(requestDto.getCouponId())

                        .orElseThrow(() -> new NotFoundException(
                            CouponMessageEnum.COUPON_NOT_FOUND.getMessage())) : null;

                BookOrderStatus bookOrderStatus = bookOrderStatusRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundException(
                        OrderMessageEnum.ORDER_STATUS_NOT_FOUND.getMessage()));

                Wrapping wrapping = Objects.nonNull(requestDto.getWrappingId())
                    ? wrappingRepository.findById(requestDto.getWrappingId())
                        .orElseThrow(() -> new NotFoundException(
                            OrderMessageEnum.WRAPPING_NOT_FOUND.getMessage())) : null;

                return BookOrderDetail.builder()
                    .bookPrice(requestDto.getPrice())
                    .wrappingPrice(requestDto.getWrappingPrice())
                    .amount(requestDto.getAmount())
                    .bookOrderStatus(bookOrderStatus)
                    .wrapping(wrapping)
                    .book(book)
                    .memberCoupon(coupon)
                    .bookOrder(bookOrder)
                    .build();
            }).collect(Collectors.toList());

        bookOrderDetailRepository.saveAll(bookOrderDetailList);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 적용 쿠폰 번호 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<Long> getUsedCouponIdByOrderCode(String orderCode) {
        return bookOrderDetailRepository.findAllByOrderCode(orderCode);
    }


    /**
     *
     * @param bookIds
     * @return
     */
    @Override
    public Boolean validateBookStock(List<Long> bookIds) {
        return null;
    }

}
