package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
public class BookReadResponseDto {

    private final String bookTitle;
    private final String bookIndex;
    private final String description;
    private final LocalDate publicatedAt;
    private final String isbn;
    private final Long regularPrice;
    private final Long price;
    private final BigDecimal discountRatio;
    private final Boolean isPackagable;
    private final String thumbnail;
    private final Long publisherId;
    private final String publisherName;
    private final Long authorId;
    private final String authorName;
    private final List<String> fileUrl;

    @Builder
    public BookReadResponseDto(String bookTitle, String bookIndex, String description,
        LocalDate publicatedAt, String isbn, Long regularPrice, Long price,
        BigDecimal discountRatio,
        Boolean isPackagable, String thumbnail, Long publisherId, String publisherName,
        Long authorId,
        String authorName, List<String> fileUrl) {
        this.bookTitle = bookTitle;
        this.bookIndex = bookIndex;
        this.description = description;
        this.publicatedAt = publicatedAt;
        this.isbn = isbn;
        this.regularPrice = regularPrice;
        this.price = price;
        this.discountRatio = discountRatio;
        this.isPackagable = isPackagable;
        this.thumbnail = thumbnail;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.authorId = authorId;
        this.authorName = authorName;
        this.fileUrl = fileUrl;
    }
}
