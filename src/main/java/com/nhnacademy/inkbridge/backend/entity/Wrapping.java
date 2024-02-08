package com.nhnacademy.inkbridge.backend.entity;

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
 * class: Wrapping.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "wrapping")
public class Wrapping {
    @Id
    @Column(name = "wrapping_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wrappingId;

    @Column(name = "wrapping_name")
    private String wrappingName;

    @Column(name = "price")
    private Long price;
}
