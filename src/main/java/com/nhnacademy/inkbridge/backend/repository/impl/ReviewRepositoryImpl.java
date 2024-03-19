package com.nhnacademy.inkbridge.backend.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.entity.QMember;
import com.nhnacademy.inkbridge.backend.entity.QReview;
import com.nhnacademy.inkbridge.backend.entity.QReviewFile;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.repository.custom.ReviewRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
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
     *
     * @param bookId
     * @return
     */
    @Override
    public List<ReviewReadResponseDto> findByBookId(Long bookId) {
        QBook book = QBook.book;
        QReview review = QReview.review;
        QFile file = QFile.file;
        QReviewFile reviewFile = QReviewFile.reviewFile;

        return from(book)
            .innerJoin(review).on(review.book.eq(book))
            .leftJoin(reviewFile).on(reviewFile.review.eq(review))
            .leftJoin(file).on(file.eq(reviewFile.file))
            .where(book.bookId.eq(bookId))
            .select(Projections.constructor(ReviewReadResponseDto.class, review.reviewTitle,
                review.reviewContent, review.registeredAt, review.score,
                list(file.fileUrl)))
            .transform(groupBy(book.bookId)
                .list(Projections.constructor(ReviewReadResponseDto.class, review.reviewTitle,
                    review.reviewContent, review.registeredAt, review.score,
                    list(Projections.constructor(String.class, file.fileUrl)))));
    }

    /**
     *
     * @param memberId
     * @return
     */
    @Override
    public List<ReviewReadResponseDto> findByMemberId(Long memberId) {
        QMember member = QMember.member;
        QReview review = QReview.review;
        QFile file = QFile.file;
        QReviewFile reviewFile = QReviewFile.reviewFile;

        return from(member)
            .innerJoin(review).on(review.member.eq(member))
            .leftJoin(reviewFile).on(reviewFile.review.eq(review))
            .leftJoin(file).on(file.eq(reviewFile.file))
            .where(member.memberId.eq(memberId))
            .select(Projections.constructor(ReviewReadResponseDto.class, review.reviewTitle,
                review.reviewContent, review.registeredAt, review.score,
                list(file.fileUrl)))
            .transform(groupBy(member.memberId)
                .list(Projections.constructor(ReviewReadResponseDto.class, review.reviewTitle,
                    review.reviewContent, review.registeredAt, review.score,
                    list(Projections.constructor(String.class, file.fileUrl)))));
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
}
