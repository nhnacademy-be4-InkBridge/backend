package com.nhnacademy.inkbridge.backend.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

/**
 * class: CategoryCoupon.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "category_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryCoupon {
    @EmbeddedId
    private Pk pk;

    @MapsId("categoryId")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @MapsId("couponId")
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;


    @Embeddable
    @EqualsAndHashCode
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Pk implements Serializable {
        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "coupon_id")
        private Long couponId;

        @Builder
        public Pk(Long categoryId, Long couponId) {
            this.categoryId = categoryId;
            this.couponId = couponId;
        }
    }
}
