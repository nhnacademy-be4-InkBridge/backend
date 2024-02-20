package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: MemberCouponRepository.
 *
 * @author JBum
 * @version 2024/02/19
 */
public interface MemberCouponRepository extends JpaRepository<MemberCoupon,String> {

    /**
     * 특정 쿠폰이 특정 회원에게 이미 발급되었는지 확인합니다.
     *
     * @param coupon 쿠폰 발급 여부를 확인할 쿠폰
     * @param member 쿠폰 발급 여부를 확인할 회원
     * @return 해당 회원이 해당 쿠폰을 이미 발급받았으면 true, 그렇지 않으면 false를 반환합니다.
     */
    boolean existsByCouponAndMember(Coupon coupon, Member member);

}
