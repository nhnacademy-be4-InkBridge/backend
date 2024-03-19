package com.nhnacademy.inkbridge.backend.dto.review;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 *  class: ReviewReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@Getter
public class ReviewReadResponseDto {

    private final String reviewTitle;
    private final String reviewContent;
    private final LocalDateTime registeredAt;
    private final Integer score;
    private final List<String> fileUrls;

    @Builder
    public ReviewReadResponseDto(String reviewTitle, String reviewContent,
        LocalDateTime registeredAt,
        Integer score, List<String> fileUrls) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.registeredAt = registeredAt;
        this.score = score;
        this.fileUrls = fileUrls;
    }
}
