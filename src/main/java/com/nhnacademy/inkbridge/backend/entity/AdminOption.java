package com.nhnacademy.inkbridge.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
 * class: AdminOption.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "admin_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    private Long settingId;

    @Column(name = "register_point")
    private Long registerPoint;

    @Column(name = "review_point")
    private Long reviewPoint;

    @Column(name = "review_photo_point")
    private Long reviewPhotoPoint;

    @Column(name = "basic_point_rate")
    private BigDecimal basicPointRate;

    @Column(name = "basic_post_price")
    private Long basicPostPrice;

    @Column(name = "setted_at")
    private LocalDateTime settedAt;
}
