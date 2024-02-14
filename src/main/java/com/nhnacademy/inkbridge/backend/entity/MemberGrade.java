package com.nhnacademy.inkbridge.backend.entity;

import java.math.BigDecimal;
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
 * class: memberGrade.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "member_grade")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "grade")
    private String grade;

    @Column(name = "point_rate")
    private BigDecimal pointRate;

    @Column(name = "standard_amount")
    private Long standardAmount;

}
