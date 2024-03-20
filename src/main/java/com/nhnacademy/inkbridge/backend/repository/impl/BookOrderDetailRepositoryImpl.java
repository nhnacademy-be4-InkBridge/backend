package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.QBookOrder;
import com.nhnacademy.inkbridge.backend.entity.QBookOrderDetail;
import com.nhnacademy.inkbridge.backend.repository.custom.BookOrderDetailRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookOrderDetailRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/03/19
 */
public class BookOrderDetailRepositoryImpl extends QuerydslRepositorySupport implements
    BookOrderDetailRepositoryCustom {

    public BookOrderDetailRepositoryImpl() {
        super(BookOrderDetail.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 사용한 쿠폰 목록
     */
    @Override
    public List<Long> findAllByOrderCode(String orderCode) {
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;
        QBookOrder bookOrder = QBookOrder.bookOrder;

        return from(bookOrderDetail)
            .innerJoin(bookOrder)
            .on(bookOrderDetail.bookOrder.eq(bookOrder))
            .where(bookOrder.orderCode.eq(orderCode)
                .and(bookOrderDetail.memberCoupon.isNotNull()))
            .select(bookOrderDetail.memberCoupon.memberCouponId)
            .fetch();
    }
}
