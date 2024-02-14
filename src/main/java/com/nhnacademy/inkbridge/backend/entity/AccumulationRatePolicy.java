package com.nhnacademy.inkbridge.backend.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

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
    private Long accumulationRatePolicyId;

    @Column(name = "accumulation_rate")
    private Integer accumulationRate;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
