package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private String bookTitle;
    private String bookIndex;
    private String description;
    private LocalDate publicatedAt;
    private String isbn;
    private Long regularPrice;
    private Long price;
    private BigDecimal discountRatio;
    private Boolean isPackagable;
    private String publisherName;

    @Builder
    public BookReadResponseDto(String bookTitle, String bookIndex, String description,
        LocalDate publicatedAt, String isbn, Long regularPrice, Long price,
        BigDecimal discountRatio,
        Boolean isPackagable, String publisherName) {
        this.bookTitle = bookTitle;
        this.bookIndex = bookIndex;
        this.description = description;
        this.publicatedAt = publicatedAt;
        this.isbn = isbn;
        this.regularPrice = regularPrice;
        this.price = price;
        this.discountRatio = discountRatio;
        this.isPackagable = isPackagable;
        this.publisherName = publisherName;
    }
}
