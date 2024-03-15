package com.nhnacademy.inkbridge.backend.dto.author;

import com.nhnacademy.inkbridge.backend.dto.book.BooksPaginationReadResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * class: AuthorReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/14
 */
@Getter
public class AuthorReadResponseDto {

    private final Page<BooksPaginationReadResponseDto> booksPaginationReadResponseDtos;
    private final AuthorInfoReadResponseDto authorInfoReadResponseDto;

    @Builder
    public AuthorReadResponseDto(
        Page<BooksPaginationReadResponseDto> booksPaginationReadResponseDtos,
        AuthorInfoReadResponseDto authorInfoReadResponseDto) {
        this.booksPaginationReadResponseDtos = booksPaginationReadResponseDtos;
        this.authorInfoReadResponseDto = authorInfoReadResponseDto;
    }
}
