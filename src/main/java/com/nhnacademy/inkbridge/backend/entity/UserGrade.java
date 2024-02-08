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

@Entity
@Table(name = "user_grade")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGrade {
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
