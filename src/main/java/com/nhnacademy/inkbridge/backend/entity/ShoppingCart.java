package com.nhnacademy.inkbridge.backend.entity;

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
 * class: ShoppingCart.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "shopping_cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "amount")
    private Integer amount;
}
