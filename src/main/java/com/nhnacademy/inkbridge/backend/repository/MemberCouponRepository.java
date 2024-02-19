package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: MemberCouponRepository.
 *
 * @author JBum
 * @version 2024/02/19
 */
public interface MemberCouponRepository extends JpaRepository<MemberCoupon,String> {

    boolean existsByCouponAndMemberAnd(Coupon coupon, Member member);

}
