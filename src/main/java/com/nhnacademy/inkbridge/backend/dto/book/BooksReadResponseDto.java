package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BooksReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BooksReadResponseDto {

    private String bookTitle;

    private Long price;

    private String publisherName;


    @Builder
    public BooksReadResponseDto(String bookTitle, Long price, String publisherName) {
        this.bookTitle = bookTitle;
        this.price = price;
        this.publisherName = publisherName;
    }
}
