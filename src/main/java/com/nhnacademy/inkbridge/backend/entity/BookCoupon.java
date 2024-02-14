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
