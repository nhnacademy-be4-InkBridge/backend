package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Builder;
import lombok.Getter;

/**
 * class: BooksReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
public class BooksReadResponseDto {

    private final Long bookId;
    private final String bookTitle;
    private final Long price;
    private final String publisherName;
    private final String authorName;

    @Builder
    public BooksReadResponseDto(Long bookId, String bookTitle, Long price, String publisherName,
        String authorName) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.price = price;
        this.publisherName = publisherName;
        this.authorName = authorName;
    }
}
