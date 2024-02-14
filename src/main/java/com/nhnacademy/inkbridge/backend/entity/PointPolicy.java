package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDate;
import javax.persistence.*;

import com.nhnacademy.inkbridge.backend.entity.enums.PolicyType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: AdminOption.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "point_policy")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_policy_id")
    private Long pointPolicyId;

    @Column(name = "policy_type")
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    @Column(name = "accumulate_point")
    private Long accumulatePoint;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
