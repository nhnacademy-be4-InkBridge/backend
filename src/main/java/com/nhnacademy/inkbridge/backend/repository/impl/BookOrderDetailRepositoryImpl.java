package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.order.OrderDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QBookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.QBookOrderStatus;
import com.nhnacademy.inkbridge.backend.entity.QCoupon;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.entity.QMemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.QWrapping;
import com.nhnacademy.inkbridge.backend.repository.custom.BookOrderDetailRepositoryCustom;
import com.querydsl.core.types.Projections;
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
 * @version 2024/03/17
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

    @Override
    public List<OrderDetailReadResponseDto> findAllByOrderId(Long orderId) {
        QBook book = QBook.book;
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;
        QCoupon coupon = QCoupon.coupon;
        QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;
        QBookOrderStatus bookOrderStatus = QBookOrderStatus.bookOrderStatus;
        QWrapping wrapping = QWrapping.wrapping;
        QFile file = QFile.file;

        return from(bookOrderDetail)
            .innerJoin(bookOrderStatus)
            .on(bookOrderDetail.bookOrderStatus.eq(bookOrderStatus))
            .innerJoin(book)
            .on(bookOrderDetail.book.eq(book))
            .innerJoin(file)
            .on(book.thumbnailFile.eq(file))
            .leftJoin(wrapping)
            .on(bookOrderDetail.wrapping.eq(wrapping))
            .leftJoin(memberCoupon)
            .on(bookOrderDetail.memberCoupon.eq(memberCoupon))
            .leftJoin(coupon)
            .on(memberCoupon.coupon.eq(coupon))
            .select(Projections.constructor(OrderDetailReadResponseDto.class,
                bookOrderDetail.orderDetailId,
                bookOrderDetail.bookPrice,
                bookOrderDetail.wrappingPrice,
                bookOrderDetail.amount,
                wrapping.wrappingName,
                bookOrderStatus.orderStatusId,
                bookOrderStatus.orderStatus,
                book.bookId,
                file.fileUrl,
                book.bookTitle,
                memberCoupon.memberCouponId,
                coupon.couponName,
                coupon.maxDiscountPrice,
                coupon.discountPrice))
            .where(bookOrderDetail.bookOrder.orderId.eq(orderId))
            .orderBy(bookOrderDetail.orderDetailId.asc())
            .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderCode 주문 코드
     * @return 주문 상세 목록
     */
    @Override
    public List<OrderDetailReadResponseDto> findAllByOrderCode(String orderCode) {
        QBook book = QBook.book;
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;
        QCoupon coupon = QCoupon.coupon;
        QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;
        QBookOrderStatus bookOrderStatus = QBookOrderStatus.bookOrderStatus;
        QWrapping wrapping = QWrapping.wrapping;
        QFile file = QFile.file;

        return from(bookOrderDetail)
            .innerJoin(bookOrderStatus)
            .on(bookOrderDetail.bookOrderStatus.eq(bookOrderStatus))
            .innerJoin(book)
            .on(bookOrderDetail.book.eq(book))
            .innerJoin(file)
            .on(book.thumbnailFile.eq(file))
            .leftJoin(wrapping)
            .on(bookOrderDetail.wrapping.eq(wrapping))
            .leftJoin(memberCoupon)
            .on(bookOrderDetail.memberCoupon.eq(memberCoupon))
            .leftJoin(coupon)
            .on(memberCoupon.coupon.eq(coupon))
            .select(Projections.constructor(OrderDetailReadResponseDto.class,
                bookOrderDetail.orderDetailId,
                bookOrderDetail.bookPrice,
                bookOrderDetail.wrappingPrice,
                bookOrderDetail.amount,
                wrapping.wrappingName,
                bookOrderStatus.orderStatusId,
                bookOrderStatus.orderStatus,
                book.bookId,
                file.fileUrl,
                book.bookTitle,
                memberCoupon.memberCouponId,
                coupon.couponName,
                coupon.maxDiscountPrice,
                coupon.discountPrice))
            .where(bookOrderDetail.bookOrder.orderCode.eq(orderCode))
            .orderBy(bookOrderDetail.orderDetailId.asc())
            .fetch();

    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 주문 상세 목록
     */
    @Override
    public List<BookOrderDetail> findOrderDetailByOrderId(Long orderId) {
        QBookOrderDetail bookOrderDetail = QBookOrderDetail.bookOrderDetail;

        return from(bookOrderDetail)
            .select(bookOrderDetail)
            .where(bookOrderDetail.bookOrder.orderId.eq(orderId))
            .fetch();
    }
}

