package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookCreateRequestDto.
 *
 * @author minm063
 * @version 2024/02/14
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookAdminCreateRequestDto {

    @NotBlank
    private String bookTitle;

    private String bookIndex;

    private String description;

    private LocalDate publicatedAt;

    @NotBlank
    @Pattern(regexp = "^\\d{13}$")
    private String isbn;

    private Long regularPrice;

    private Long price;

    private BigDecimal discountRatio;

    private Integer stock;

    private Boolean isPackagable;

    private Long statusId;

    private Long publisherId;

    private Long thumbnailId;

    @Builder
    public BookAdminCreateRequestDto(String bookTitle, String bookIndex, String description,
        LocalDate publicatedAt, String isbn, Long regularPrice, Long price,
        BigDecimal discountRatio,
        Integer stock, Boolean isPackagable, Long statusId, Long publisherId, Long thumbnailId) {
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
        this.statusId = statusId;
        this.publisherId = publisherId;
        this.thumbnailId = thumbnailId;
    }
}
