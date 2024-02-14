package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookOrder.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_order")
public class BookOrder {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "ship_date")
    private LocalDateTime shipDate;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "receiver_number")
    private String receiverNumber;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "orderer")
    private String orderer;

    @Column(name = "orderer_number")
    private String ordererNumber;

    @Column(name = "orderer_email")
    private String ordererEmail;

    @Column(name = "use_point")
    private Long usePoint;

    @Column(name = "total_price")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "delivery_policy_id")
    private DeliveryPolicy deliveryPolicy;

    @ManyToOne
    @JoinColumn(name = "accumulation_rate_policy_id")
    private AccumulationRatePolicy accumulationRatePolicy;
}
