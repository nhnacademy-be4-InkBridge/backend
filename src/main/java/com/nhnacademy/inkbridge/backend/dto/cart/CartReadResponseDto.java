package com.nhnacademy.inkbridge.backend.dto.cart;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * class: CartReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/09
 */
@Getter
public class CartReadResponseDto {

    private final Long bookId;
    private final String bookTitle;
    private final Long regularPrice;
    private final Long price;
    private final BigDecimal discountRatio;
    private final Integer stock;
    private final Boolean isPackagable;
    private final String thumbnail;

    @Builder
    public CartReadResponseDto(Long bookId, String bookTitle, Long regularPrice, Long price,
        BigDecimal discountRatio, Integer stock, Boolean isPackagable, String thumbnail) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.regularPrice = regularPrice;
        this.price = price;
        this.discountRatio = discountRatio;
        this.stock = stock;
        this.isPackagable = isPackagable;
        this.thumbnail = thumbnail;
    }
}
