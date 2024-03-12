package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
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

    @Override
    public Optional<OrderPayInfoReadResponseDto> findOrderPayByOrderId(String orderId) {
        QBookOrder bookOrder = QBookOrder.bookOrder;

        OrderPayInfoReadResponseDto orderPayInfoReadResponseDto = from(bookOrder)
            .select(Projections.constructor(OrderPayInfoReadResponseDto.class,
                bookOrder.orderId,
                bookOrder.orderName,
                bookOrder.totalPrice))
            .where(bookOrder.orderId.eq(orderId))
            .fetchOne();
        return Optional.of(orderPayInfoReadResponseDto);
    }
}
