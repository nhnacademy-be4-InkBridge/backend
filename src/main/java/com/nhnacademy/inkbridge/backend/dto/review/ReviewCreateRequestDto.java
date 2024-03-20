package com.nhnacademy.inkbridge.backend.dto.review;

import lombok.Builder;
import lombok.Getter;

/**
 * class: ReviewCreateRequestDto.
 *
 * @author minm063
 * @version 2024/03/19
 */
@Getter
public class ReviewCreateRequestDto {

    private final Long bookId;
    private final Long orderDetailId;
    private final String reviewTitle;
    private final String reviewContent;
    private final Integer score;

    @Builder
    public ReviewCreateRequestDto(Long bookId, Long orderDetailId,
        String reviewTitle,
        String reviewContent, Integer score) {
        this.bookId = bookId;
        this.orderDetailId = orderDetailId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.score = score;
    }
}
