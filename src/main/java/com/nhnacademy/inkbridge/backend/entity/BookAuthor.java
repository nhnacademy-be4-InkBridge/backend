package com.nhnacademy.inkbridge.backend.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

/**
 * class: BookAuthor.
 *
 * @author nhn
 * @version 2024/02/08
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_author")
public class BookAuthor {

    @EmbeddedId
    private Pk pk;

    @MapsId("authorId")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "author_id")
        private Long authorId;
        @Column(name = "book_id")
        private Long bookId;

        @Builder
        public Pk(Long authorId, Long bookId) {
            this.authorId = authorId;
            this.bookId = bookId;
        }
    }
}
