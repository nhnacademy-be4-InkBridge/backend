package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: DeliveryPolicy.
 *
 * @author jangjaehun
 * @version 2024/02/14
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "delivery_policy")
public class DeliveryPolicy {

    @Id
    @Column(name = "delivery_policy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryPolicyId;

    @Column(name = "delivery_price")
    private Long deliveryPrice;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
