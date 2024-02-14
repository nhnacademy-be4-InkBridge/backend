package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Pay.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "pay")
public class Pay {
    @Id
    @Column(name = "pay_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private String status;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "total_Amount")
    private Long totalAmount;

    @Column(name = "balance_Amount")
    private Long balanceAmount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private BookOrder order;
}
