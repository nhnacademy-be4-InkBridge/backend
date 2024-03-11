package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookIdNameReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.QBookCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCoupon;
import com.nhnacademy.inkbridge.backend.repository.custom.CouponCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookRepositoryImpl.
 *
 * @author JBum
 * @version 2024/03/08
 */
public class CouponRepositoryImpl extends QuerydslRepositorySupport implements
    CouponCustomRepository {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CouponDetailReadResponseDto> findDetailCoupon(String couponId) {
        QCoupon coupon = QCoupon.coupon;
        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;
        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;

        // 카테고리와 책 정보 가져오기
        List<CategoryReadResponseDto> categories = from(categoryCoupon)
            .select(Projections.constructor(
                CategoryReadResponseDto.class,
                categoryCoupon.category.categoryId,
                categoryCoupon.category.categoryName))
            .where(categoryCoupon.coupon.couponId.eq(couponId))
            .fetch();

        List<BookIdNameReadResponseDto> books = from(bookCoupon)
            .select(Projections.constructor(
                BookIdNameReadResponseDto.class,
                bookCoupon.book.bookId,
                bookCoupon.book.bookTitle))
            .where(bookCoupon.coupon.couponId.eq(couponId))
            .fetch();

        // 쿠폰 정보 가져오기
        CouponDetailReadResponseDto couponDetail = from(coupon)
            .where(coupon.couponId.eq(couponId))
            .select(Projections.constructor(
                CouponDetailReadResponseDto.class,
                coupon.couponId,
                coupon.couponName,
                coupon.minPrice,
                coupon.discountPrice,
                coupon.maxDiscountPrice,
                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", coupon.basicIssuedDate),
                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", coupon.basicExpiredDate),
                coupon.validity,
                coupon.couponType.typeName,
                coupon.isBirth,
                coupon.couponStatus.couponStatusName))
            .fetchFirst();

        // 카테고리와 책 정보를 쿠폰 DTO에 설정
        if (couponDetail != null) {
            couponDetail.setRelation(categories, books);
        }

        return Optional.ofNullable(couponDetail);
    }
//    @Override
//    public List<MemberCouponReadResponseDto> findOrderCoupons(Long memberId,
//        BookCategoriesDto bookCategoriesDto) {
//        QCoupon coupon = QCoupon.coupon;
//        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;
//        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;
//        QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;
////        1. 맴버가 일치하고 책이 일치해야함.
////        2. 맴버가 일치하고 카테고리가 일치해야함.
////        3. 멤버가일치하고 카테고리 그리고 책이 일치하지않아야함
//        List<MemberCouponReadResponseDto> result = from(memberCoupon)
//            .leftJoin(coupon).on(memberCoupon.coupon.couponId.eq(coupon.couponId))
//            .leftJoin(bookCoupon).on(coupon.couponId.eq(bookCoupon.coupon.couponId))
//            .leftJoin(categoryCoupon).on(coupon.couponId.eq(categoryCoupon.coupon.couponId))
//            .where(memberCoupon.member.memberId.eq(memberId)
//                .and(memberCoupon.usedAt.isNull())
//                .and(memberCoupon.coupon.couponStatus.couponStatusId.eq(1))
//                .and(bookCoupon.book.bookId.eq(bookCategoriesDto.getBookId())
//                    .or(categoryCoupon.category.categoryId.in(bookCategoriesDto.getCategoryIds()))
//                    .or(categoryCoupon.category.categoryId.isNull()
//                        .and(bookCoupon.book.bookId.isNull()))))
//            .select(Projections.constructor(MemberCouponReadResponseDto.class,
//                memberCoupon.memberCouponId,
//                memberCoupon.expiredAt,
//                memberCoupon.usedAt,
//                coupon.couponName,
//                coupon.minPrice,
//                coupon.discountPrice,
//                coupon.maxDiscountPrice,
//                coupon.couponType.couponTypeId,
//                coupon.couponType.typeName,
//                coupon.isBirth,
//                coupon.couponStatus.couponStatusId,
//                coupon.couponStatus.couponStatusName))
//            .fetch();
//        return result;
//    }
}
