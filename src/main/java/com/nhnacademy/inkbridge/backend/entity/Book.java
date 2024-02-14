package com.nhnacademy.inkbridge.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Book.
 *
 * @author nhn
 * @version 2024/02/08
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_index")
    private String bookIndex;

    @Column(name = "description")
    private String description;

    @Column(name = "publicated_at")
    private LocalDate publicatedAt;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "regular_price")
    private Long regularPrice;

    @Column(name = "price")
    private Long price;

    @Column(name = "discount_ratio")
    private BigDecimal discountRatio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "is_packagable")
    private Boolean isPackagable;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private BookStatus bookStatus;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToOne
    @JoinColumn(name = "thumbnail_id")
    private File thumbnailFile;
}
