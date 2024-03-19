package com.nhnacademy.inkbridge.backend.dto.book;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewReadResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 *  class: BookReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@Getter
public class BookReadResponseDto {

    private final BookDetailReadResponseDto bookDetailReadResponseDto;
    private final List<ReviewReadResponseDto> reviewReadResponseDtos;
    private final ReviewAverageReadResponseDto reviewAverageReadResponseDto;

    @Builder
    public BookReadResponseDto(BookDetailReadResponseDto bookDetailReadResponseDto,
        List<ReviewReadResponseDto> reviewReadResponseDtos,
        ReviewAverageReadResponseDto reviewAverageReadResponseDto) {
        this.bookDetailReadResponseDto = bookDetailReadResponseDto;
        this.reviewReadResponseDtos = reviewReadResponseDtos;
        this.reviewAverageReadResponseDto = reviewAverageReadResponseDto;
    }
}
