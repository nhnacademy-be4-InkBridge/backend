package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.entity.enums.OrderStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookOrderDetail.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_order_detail")
public class BookOrderDetail {
    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @Column(name = "book_price")
    private Long bookPrice;

    @Column(name = "wrapping_price")
    private Long wrappingPrice;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "amount")
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "wrapping_id")
    private Wrapping wrapping;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private BookOrder bookOrder;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "user_coupon_id")
    private UserCoupon userCoupon;
}
