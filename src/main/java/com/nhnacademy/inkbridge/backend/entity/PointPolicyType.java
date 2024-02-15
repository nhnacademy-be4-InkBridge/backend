package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: PointPolicyType.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "point_policy_type")
public class PointPolicyType {
    @Id
    @Column(name = "point_policy_type_id")
    private Integer pointPolicyTypeId;

    @Column(name = "policy_type")
    private String policyType;
}
