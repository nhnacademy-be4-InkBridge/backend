package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Builder;
import lombok.Getter;

/**
 * class: BooksAdminReadResponse.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
public class BooksAdminReadResponseDto {
    private final Long bookId;
    private final String bookTitle;
    private final String authorName;
    private final String publisherName;
    private final String statusName;

    @Builder
    public BooksAdminReadResponseDto(Long bookId, String bookTitle, String authorName, String publisherName,
        String statusName) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.statusName = statusName;
    }
}
