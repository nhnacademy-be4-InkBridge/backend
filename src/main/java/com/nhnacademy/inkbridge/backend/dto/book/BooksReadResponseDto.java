package com.nhnacademy.inkbridge.backend.dto.book;

import java.util.List;
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
@NoArgsConstructor
public class BooksReadResponseDto {

    private Long bookId;
    private String bookTitle;
    private Long price;
    private String publisherName;
    private List<String> authorName;
    private String fileUrl;

    @Builder
    public BooksReadResponseDto(Long bookId, String bookTitle, Long price, String publisherName,
        List<String> authorName, String fileUrl) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.price = price;
        this.publisherName = publisherName;
        this.authorName = authorName;
        this.fileUrl = fileUrl;
    }
}
