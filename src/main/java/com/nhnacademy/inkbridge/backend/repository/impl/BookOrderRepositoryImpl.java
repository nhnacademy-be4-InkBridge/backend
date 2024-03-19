package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.QBookOrder;
import com.nhnacademy.inkbridge.backend.repository.custom.BookOrderRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookOrderRepositoryimpl.
 *
 * @author jangjaehun
 * @version 2024/03/12
 */
public class BookOrderRepositoryImpl extends QuerydslRepositorySupport implements
    BookOrderRepositoryCustom {

    public BookOrderRepositoryImpl() {
        super(BookOrder.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 결제 정보
     */
    @Override
    public Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderCode(String orderCode) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        OrderPayInfoReadResponseDto orderPayInfoReadResponseDto = from(bookOrder)
            .select(Projections.constructor(OrderPayInfoReadResponseDto.class,
                bookOrder.orderCode,
                bookOrder.orderName,
                bookOrder.totalPrice))
            .where(bookOrder.orderCode.eq(orderCode))
            .fetchOne();
        return Optional.of(orderPayInfoReadResponseDto).or(Optional::empty);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 코드
     * @return 주문 결제 정보
     */
    @Override
    public Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderId(Long orderId) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        OrderPayInfoReadResponseDto orderPayInfoReadResponseDto = from(bookOrder)
            .select(Projections.constructor(OrderPayInfoReadResponseDto.class,
                bookOrder.orderCode,
                bookOrder.orderName,
                bookOrder.totalPrice))
            .where(bookOrder.orderId.eq(orderId))
            .fetchOne();
        return Optional.of(orderPayInfoReadResponseDto).or(Optional::empty);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 사용한 포인트 정보
     */
    @Override
    public Optional<OrderedMemberPointReadResponseDto> findUsedPointByOrderCode(String orderCode) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return Optional.of(from(bookOrder)
            .select(Projections.constructor(OrderedMemberPointReadResponseDto.class,
                bookOrder.member.memberId,
                bookOrder.usePoint,
                bookOrder.totalPrice))
            .where(bookOrder.orderCode.eq(orderCode))
            .fetchOne()).or(Optional::empty);
    }
}
