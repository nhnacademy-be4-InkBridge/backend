package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDateTime;
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
 * class: PointHistory.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory {
    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "point")
    private Long point;

    @Column(name = "accrued_at")
    private LocalDateTime accruedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;
}
