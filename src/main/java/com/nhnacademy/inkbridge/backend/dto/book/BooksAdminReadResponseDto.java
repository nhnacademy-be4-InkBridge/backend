package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: BookAdminReadResponse.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
@Setter
@NoArgsConstructor
public class BooksAdminReadResponseDto {

    private String bookTitle;

    private String authorName;

    private String publisherName;

    private String statusName;

    @Builder
    public BooksAdminReadResponseDto(String bookTitle, String authorName, String publisherName,
        String statusName) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.statusName = statusName;
    }
}
