package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Coupon.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "min_price")
    private Long minPrice;

    @Column(name = "max_discount_price")
    private Long maxDiscountPrice;

    @Column(name = "discount_price")
    private Long discountPrice;

    @Column(name = "basic_issued_at")
    private LocalDate basicIssuedAt;

    @Column(name = "basic_expired_at")
    private LocalDate basicExpiredAt;

    @Column(name = "validity")
    private Integer validity;

    @OneToOne
    @JoinColumn(name = "coupon_type_id")
    private CouponType couponType;
}
