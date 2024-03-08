package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoriesDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.entity.QBookCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.QCoupon;
import com.nhnacademy.inkbridge.backend.entity.QMemberCoupon;
import com.nhnacademy.inkbridge.backend.repository.custom.MemberCouponCustomRepository;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookRepositoryImpl.
 *
 * @author JBum
 * @version 2024/03/08
 */
public class MemberCouponRepositoryImpl extends QuerydslRepositorySupport implements
    MemberCouponCustomRepository {

    public MemberCouponRepositoryImpl() {
        super(MemberCoupon.class);
    }

    @Override
    public List<MemberCouponReadResponseDto> findOrderCoupons(Long memberId,
        BookCategoriesDto bookCategoriesDto) {
        QCoupon coupon = QCoupon.coupon;
        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;
        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;
        QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;
//        1. 맴버가 일치하고 책이 일치해야함.
//        2. 맴버가 일치하고 카테고리가 일치해야함.
//        3. 멤버가일치하고 카테고리 그리고 책이 일치하지않아야함
        List<MemberCouponReadResponseDto> result = from(memberCoupon)
            .leftJoin(coupon).on(memberCoupon.coupon.couponId.eq(coupon.couponId))
            .leftJoin(bookCoupon).on(coupon.couponId.eq(bookCoupon.coupon.couponId))
            .leftJoin(categoryCoupon).on(coupon.couponId.eq(categoryCoupon.coupon.couponId))
            .where(memberCoupon.member.memberId.eq(memberId)
                .and(memberCoupon.usedAt.isNull())
                .and(bookCoupon.book.bookId.eq(bookCategoriesDto.getBookId())
                    .or(categoryCoupon.category.categoryId.in(bookCategoriesDto.getCategoryIds()))
                    .or(categoryCoupon.category.categoryId.isNull()
                        .and(bookCoupon.book.bookId.isNull()))))
            .select(Projections.constructor(MemberCouponReadResponseDto.class,
                memberCoupon.memberCouponId,
                memberCoupon.expiredAt,
                memberCoupon.usedAt,
                coupon.couponName,
                coupon.minPrice,
                coupon.discountPrice,
                coupon.maxDiscountPrice,
                coupon.couponType,
                coupon.isBirth,
                coupon.couponStatus))
            .fetch();
        return result;
    }

//    /**
//     * {@inheritDoc}
//     */
////    @Override
//    public Page<BooksReadResponseDto> findAllBooks(Pageable pageable) {
//        QBook book = QBook.book;
//        QAuthor author = QAuthor.author;
//        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
//        QPublisher publisher = QPublisher.publisher;
//        QBookStatus bookStatus = QBookStatus.bookStatus;
//
//        List<BooksReadResponseDto> content = from(book)
//            .innerJoin(publisher)
//            .on(book.publisher.eq(publisher))
//            .innerJoin(bookStatus)
//            .on(bookStatus.eq(book.bookStatus))
//            .innerJoin(bookAuthor)
//            .on(bookAuthor.book.eq(book))
//            .innerJoin(author)
//            .on(author.eq(bookAuthor.author))
//            .where(bookStatus.statusId.eq(1L))
//            .select(Projections.constructor(BooksReadResponseDto.class,
//                book.bookId, book.bookTitle, book.price, publisher.publisherName,
//                author.authorName, book.thumbnailFile.fileUrl))
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();
//
//        return new PageImpl<>(content, pageable, content.size());
//    }
}
