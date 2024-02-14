package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.entity.enums.Auth;
import com.nhnacademy.inkbridge.backend.entity.enums.Status;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: member.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "member_point")
    private Long memberPoint;

    @Column(name = "auth")
    @Enumerated(EnumType.STRING)
    private Auth auth;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private MemberGrade memberGrade;

    @Column(name = "withdraw_at")
    private LocalDateTime withdrawAt;

}
