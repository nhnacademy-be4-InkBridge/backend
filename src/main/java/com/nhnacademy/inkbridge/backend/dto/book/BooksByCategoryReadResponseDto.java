package com.nhnacademy.inkbridge.backend.dto.book;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryNameReadResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * class: BooksReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/13
 */
@Getter
public class BooksByCategoryReadResponseDto {

    private final Page<BooksPaginationReadResponseDto> booksPaginationReadResponseDtos;
    private final List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDto;
    private final CategoryNameReadResponseDto categoryNameReadResponseDto;

    @Builder
    public BooksByCategoryReadResponseDto(
        Page<BooksPaginationReadResponseDto> booksPaginationReadResponseDtos,
        List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDto,
        CategoryNameReadResponseDto categoryNameReadResponseDto) {
        this.booksPaginationReadResponseDtos = booksPaginationReadResponseDtos;
        this.authorPaginationReadResponseDto = authorPaginationReadResponseDto;
        this.categoryNameReadResponseDto = categoryNameReadResponseDto;
    }
}
