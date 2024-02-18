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

    private final String bookTitle;

    private final Long price;

    private final String publisherName;


    @Builder
    public BooksReadResponseDto(String bookTitle, Long price, String publisherName) {
        this.bookTitle = bookTitle;
        this.price = price;
        this.publisherName = publisherName;
    }
}
