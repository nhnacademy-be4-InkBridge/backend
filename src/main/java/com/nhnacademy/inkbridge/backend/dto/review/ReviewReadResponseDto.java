package com.nhnacademy.inkbridge.backend.dto.review;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 *  class: ReviewReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/20
 */
@Getter
public class ReviewReadResponseDto {

    private final Page<ReviewDetailReadResponseDto> reviewDetailReadResponseDtos;
    private final Map<Long, List<String>> reviewFiles;

    @Builder
    public ReviewReadResponseDto(Page<ReviewDetailReadResponseDto> reviewDetailReadResponseDtos,
        Map<Long, List<String>> reviewFiles) {
        this.reviewDetailReadResponseDtos = reviewDetailReadResponseDtos;
        this.reviewFiles = reviewFiles;
    }
}
