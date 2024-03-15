package com.nhnacademy.inkbridge.backend.dto.book;

import java.util.List;
import lombok.Getter;

/**
 * class: AuthorPaginationReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/13
 */
@Getter
public class AuthorPaginationReadResponseDto {

    private final Long bookId;
    private final List<String> authorName;

    public AuthorPaginationReadResponseDto(Long bookId, List<String> authorName) {
        this.bookId = bookId;
        this.authorName = authorName;
    }
}
