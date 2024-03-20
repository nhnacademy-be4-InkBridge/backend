package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewDetailReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *  class: ReviewRepositoryCustom.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@NoRepositoryBean
public interface ReviewRepositoryCustom {


    Page<ReviewDetailReadResponseDto> findByBookId(Pageable pageable, Long bookId);

    Page<ReviewDetailReadResponseDto> findByMemberId(Pageable pageable, Long memberId);

    /**
     * 도서 번호로 리뷰 평점의 평균값을 계산하는 메서드입니다.
     *
     * @param bookId Long
     * @return ReviewAverageReadResponseDto
     */
    ReviewAverageReadResponseDto avgReview(Long bookId);
}
