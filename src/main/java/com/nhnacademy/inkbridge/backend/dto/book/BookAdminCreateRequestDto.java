package com.nhnacademy.inkbridge.backend.dto.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

/**
 * class: BookCreateRequestDto.
 *
 * @author minm063
 * @version 2024/02/14
 */
@Getter
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
}
