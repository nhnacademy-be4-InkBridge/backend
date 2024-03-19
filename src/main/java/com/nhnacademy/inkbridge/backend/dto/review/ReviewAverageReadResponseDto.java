package com.nhnacademy.inkbridge.backend.dto.review;

import lombok.Builder;
import lombok.Getter;

/**
 *  class: ReviewAverageReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@Getter
public class ReviewAverageReadResponseDto {

    private final Double avg;

    @Builder
    public ReviewAverageReadResponseDto(Double avg) {
        this.avg = avg;
    }
}
