package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * class: ReviewService.
 *
 * @author minm063
 * @version 2024/03/19
 */
public interface ReviewService {

    /**
     * 회원 아이디로 리뷰 목록을 조회하는 메서드입니다.
     *
     * @param memberId Long
     * @return ReviewReadResponseDto
     */
    ReviewReadResponseDto getReviewsByMember(Pageable pageable, Long memberId);


    ReviewReadResponseDto getReviewsByBookId(Pageable pageable, Long bookId);

    /**
     * @param memberId
     * @param reviewCreateRequestDto ReviewCreateRequestDto
     * @param fileIds
     */
    void createReview(Long memberId, ReviewCreateRequestDto reviewCreateRequestDto,
        List<File> fileIds);

    /**
     * @param memberId
     * @param reviewId
     * @param reviewCreateRequestDto
     * @param files
     */
    void updateReview(Long memberId, Long reviewId, ReviewCreateRequestDto reviewCreateRequestDto,
        List<File> files);
}
