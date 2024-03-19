package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *  class: ReviewRepositoryCustom.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@NoRepositoryBean
public interface ReviewRepositoryCustom {

    List<ReviewReadResponseDto> findByBookId(Long bookId);

    List<ReviewReadResponseDto> findByMemberId(Long memberId);

    /**
     * 도서 번호로 리뷰 평점의 평균값을 계산하는 메서드입니다.
     *
     * @param bookId Long
     * @return ReviewAverageReadResponseDto
     */
    ReviewAverageReadResponseDto avgReview(Long bookId);
}
