package com.nhnacademy.inkbridge.backend.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookCategory.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_category")
public class BookCategory {
    @EmbeddedId
    private Pk pk;

    @MapsId("categoryId")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "book_id")
        private Long bookId;

        @Builder
        public Pk(Long categoryId, Long bookId) {
            this.categoryId = categoryId;
            this.bookId = bookId;
        }
    }
}
