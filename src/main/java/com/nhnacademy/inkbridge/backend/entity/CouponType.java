package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CouponType.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "coupon_type")
public class CouponType {
    @Id
    @Column(name = "coupon_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponTypeId;

    @Column(name = "type_name")
    private String typeName;
}
