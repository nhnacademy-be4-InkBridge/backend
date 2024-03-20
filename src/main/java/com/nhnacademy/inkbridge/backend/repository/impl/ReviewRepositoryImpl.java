package com.nhnacademy.inkbridge.backend.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QMember;
import com.nhnacademy.inkbridge.backend.entity.QReview;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.repository.custom.ReviewRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 *  class: ReviewRepositoryImpl.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
public class ReviewRepositoryImpl extends QuerydslRepositorySupport implements
    ReviewRepositoryCustom {

    private static final Double DEFAULT_AVG = 0.0;

    public ReviewRepositoryImpl() {
        super(Review.class);
    }

    /**
     * @param bookId
     * @return
     */
    @Override
    public Page<ReviewDetailReadResponseDto> findByBookId(Pageable pageable, Long bookId) {
        QBook book = QBook.book;
        QReview review = QReview.review;

        List<ReviewDetailReadResponseDto> content = from(book)
            .innerJoin(review).on(review.book.eq(book))
            .where(book.bookId.eq(bookId))
            .select(Projections.constructor(ReviewDetailReadResponseDto.class, review.reviewTitle,
                review.reviewContent, review.registeredAt, review.score))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .transform(groupBy(review.reviewId)
                .list(Projections.constructor(ReviewDetailReadResponseDto.class, review.reviewTitle,
                    review.reviewContent.coalesce(""), review.registeredAt, review.score)));

        return new PageImpl<>(content, pageable, getCount());
    }

    /**
     * @param memberId
     * @return
     */
    @Override
    public Page<ReviewDetailReadResponseDto> findByMemberId(Pageable pageable, Long memberId) {
        QMember member = QMember.member;
        QReview review = QReview.review;

        List<ReviewDetailReadResponseDto> content = from(member)
            .innerJoin(review).on(review.member.eq(member))
            .where(member.memberId.eq(memberId))
            .select(Projections.constructor(ReviewDetailReadResponseDto.class, review.reviewTitle,
                review.reviewContent, review.registeredAt, review.score))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .transform(groupBy(review.reviewId)
                .list(Projections.constructor(ReviewDetailReadResponseDto.class, review.reviewTitle,
                    review.reviewContent, review.registeredAt, review.score)));

        return new PageImpl<>(content, pageable, getCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewAverageReadResponseDto avgReview(Long bookId) {
        QReview review = QReview.review;
        QBook book = QBook.book;

        return from(book)
            .innerJoin(review).on(review.book.bookId.eq(book.bookId))
            .where(book.bookId.eq(bookId))
            .select(
                Projections.constructor(ReviewAverageReadResponseDto.class,
                    review.score.avg().coalesce(DEFAULT_AVG)))
            .fetchOne();
    }

    private Long getCount() {
        QReview review = QReview.review;

        return from(review)
            .select(review.count())
            .fetchOne();
    }

}
