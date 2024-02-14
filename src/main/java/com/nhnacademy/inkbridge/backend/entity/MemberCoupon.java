package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: memberCoupon.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_coupon")
public class MemberCoupon {
    @Id
    @Column(name = "member_coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCouponId;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

}
