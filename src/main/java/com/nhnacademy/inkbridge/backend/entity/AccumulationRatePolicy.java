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
 * class: AccumulationRatePolicy.
 *
 * @author jangjaehun
 * @version 2024/02/14
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "accumulation_rate_policy")
public class AccumulationRatePolicy {

    @Id
    @Column(name = "accumulation_rate_policy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accumulationRatePolicyId;

    @Column(name = "accumulation_rate")
    private Integer accumulationRate;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
