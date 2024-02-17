package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Builder;
import lombok.Getter;

/**
 * class: BookAdminUpdateResponseDto.
 *
 * @author minm063
 * @version 2024/02/16
 */
@Getter
public class BookAdminUpdateResponseDto {

    private final Long bookId;

    @Builder
    public BookAdminUpdateResponseDto(Long bookId) {
        this.bookId = bookId;
    }
}
