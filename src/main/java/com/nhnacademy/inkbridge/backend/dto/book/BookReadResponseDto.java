package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
@NoArgsConstructor
public class BookReadResponseDto {

    private String bookTitle;
    private String bookIndex;
    private String description;
    private LocalDate publicatedAt;
    private String isbn;
    private Long regularPrice;
    private Long price;
    private BigDecimal discountRatio;
    private Boolean isPackagable;
    private String thumbnail;
    private Long publisherId;
    private String publisherName;
    private Long authorId;
    private String authorName;
    private Long wish;
    private Set<String> fileUrl;
    private Set<String> tagName;
    private Set<String> categoryName;

    @Builder
    public BookReadResponseDto(String bookTitle, String bookIndex, String description,
        LocalDate publicatedAt, String isbn, Long regularPrice, Long price,
        BigDecimal discountRatio,
        Boolean isPackagable, String thumbnail, Long publisherId, String publisherName,
        Long authorId,
        String authorName, Long wish, Set<String> fileUrl, Set<String> tagName,
        Set<String> categoryName) {
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
        this.wish = wish;
        this.fileUrl = fileUrl;
        this.tagName = tagName;
        this.categoryName = categoryName;
    }
}
