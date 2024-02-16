package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: BookAdminReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
@Setter
@NoArgsConstructor
public class BookAdminReadResponseDto {

    private String bookTitle;

    private String bookIndex;

    private String description;

    private LocalDate publicatedAt;

    private String isbn;

    private Long regularPrice;

    private Long price;

    private BigDecimal discountRatio;

    private Integer stock;

    private Boolean isPackagable;

    // publisher
    private String publisherName;

    // status
    private String statusName;

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
