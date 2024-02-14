package com.nhnacademy.inkbridge.backend.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

/**
 * class: BookCoupon.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "book_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCoupon {
    @EmbeddedId
    private Pk pk;

    @MapsId("couponId")
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    @Embeddable
    @EqualsAndHashCode
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Pk implements Serializable {
        @Column(name = "coupon_id")
        private Long couponId;

        @Column(name = "book_id")
        private Long bookId;

        @Builder
        public Pk(Long couponId, Long bookId) {
            this.couponId = couponId;
            this.bookId = bookId;
        }
    }
}
