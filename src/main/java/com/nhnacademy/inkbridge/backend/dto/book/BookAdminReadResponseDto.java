package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookAdminReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
public class BookAdminReadResponseDto {

    private final String bookTitle;

    private final String bookIndex;

    private final String description;

    private final LocalDate publicatedAt;

    private final String isbn;

    private final Long regularPrice;

    private final Long price;

    private final BigDecimal discountRatio;

    private final Integer stock;

    private final Boolean isPackagable;

    private final String publisherName;

    private final String statusName;

    @Builder
    public BookAdminReadResponseDto(String bookTitle, String bookIndex, String description,
        LocalDate publicatedAt, String isbn, Long regularPrice, Long price,
        BigDecimal discountRatio,
        Integer stock, Boolean isPackagable, String publisherName, String statusName) {
        this.bookTitle = bookTitle;
        this.bookIndex = bookIndex;
        this.description = description;
        this.publicatedAt = publicatedAt;
        this.isbn = isbn;
        this.regularPrice = regularPrice;
        this.price = price;
        this.discountRatio = discountRatio;
        this.stock = stock;
        this.isPackagable = isPackagable;
        this.publisherName = publisherName;
        this.statusName = statusName;
    }
}
