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
public class ReviewUpdateRequestDto {

    private final String reviewTitle;
    private final String reviewContent;
    private final Integer score;

    @Builder
    public ReviewUpdateRequestDto(String reviewTitle, String reviewContent, Integer score) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.score = score;
    }
}
