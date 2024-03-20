package com.nhnacademy.inkbridge.backend.dto.review;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 *  class: ReviewReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@Getter
public class ReviewDetailReadResponseDto {

    private final String reviewTitle;
    private final String reviewContent;
    private final LocalDateTime registeredAt;
    private final Integer score;

    @Builder
    public ReviewDetailReadResponseDto(String reviewTitle, String reviewContent,
        LocalDateTime registeredAt, Integer score) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.registeredAt = registeredAt;
        this.score = score;
    }
}
