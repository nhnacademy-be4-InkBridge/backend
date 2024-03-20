package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Pay;
import com.nhnacademy.inkbridge.backend.entity.QPay;
import com.nhnacademy.inkbridge.backend.repository.custom.PayRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: PayRepositoryImpl.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
public class PayRepositoryImpl extends QuerydslRepositorySupport implements PayRepositoryCustom {


    public PayRepositoryImpl() {
        super(Pay.class);
    }

    @Override
    public Optional<PayReadResponseDto> findPayByPayId(Long payId) {
        QPay pay = QPay.pay;
        return Optional.ofNullable(from(pay)
            .select(Projections.constructor(PayReadResponseDto.class,
                pay.payId,
                pay.paymentKey,
                pay.method,
                pay.status,
                pay.requestedAt,
                pay.approvedAt,
                pay.balanceAmount,
                pay.vat,
                pay.isPartialCancelable,
                pay.provider))
            .where(pay.payId.eq(payId))
            .fetchOne());
    }

    @Override
    public Optional<PayReadResponseDto> findPayByOrderId(Long orderId) {
        QPay pay = QPay.pay;
        return Optional.ofNullable(from(pay)
            .select(Projections.constructor(PayReadResponseDto.class,
                pay.payId,
                pay.paymentKey,
                pay.method,
                pay.status,
                pay.requestedAt,
                pay.approvedAt,
                pay.balanceAmount,
                pay.vat,
                pay.isPartialCancelable,
                pay.provider))
            .where(pay.order.orderId.eq(orderId))
            .fetchOne());
    }

    @Override
    public Optional<PayReadResponseDto> findPayByOrderCode(String orderCode) {
        QPay pay = QPay.pay;
        return Optional.ofNullable(from(pay)
            .select(Projections.constructor(PayReadResponseDto.class,
                pay.payId,
                pay.paymentKey,
                pay.method,
                pay.status,
                pay.requestedAt,
                pay.approvedAt,
                pay.balanceAmount,
                pay.vat,
                pay.isPartialCancelable,
                pay.provider))
            .where(pay.order.orderCode.eq(orderCode))
            .fetchOne());
    }
}
