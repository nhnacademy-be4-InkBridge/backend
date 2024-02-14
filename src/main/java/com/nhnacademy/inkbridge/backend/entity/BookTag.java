package com.nhnacademy.inkbridge.backend.entity;


import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

/**
 * class: BookTag.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_tag")
public class BookTag {
    @EmbeddedId
    private Pk pk;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "book_id")
        private Long bookId;
        @Column(name = "tag_id")
        private Long tagId;

        @Builder
        public Pk(Long bookId, Long tagId) {
            this.bookId = bookId;
            this.tagId = tagId;
        }
    }
}
