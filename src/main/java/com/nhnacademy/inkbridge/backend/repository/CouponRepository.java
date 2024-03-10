package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: CouponRepository.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponRepository extends JpaRepository<Coupon, String> {

    /**
     * 중복된 이름의 쿠폰이 존재하는지 찾는 메소드.
     *
     * @param name 이름
     * @return 중복이면 true, 아니면 false
     */
    boolean existsByCouponName(String name);

    /**
     * 쿠폰상태로 리스트를 페이지 갯수에맞게 찾아주는 메소드.
     *
     * @param couponStatus 쿠폰상태
     * @param pageable     페이지, 사이즈
     * @return 쿠폰상태를 기준으로 페이지리스트에 저장된 쿠폰들
     */
    Page<CouponReadResponseDto> findByCouponStatus(CouponStatus couponStatus, Pageable pageable);

    /**
     * 쿠폰상태Id로 리스트를 페이지 갯수에맞게 찾아주는 메소드.
     *
     * @param couponStatus 쿠폰상태
     * @param pageable     페이지, 사이즈
     * @return 쿠폰상태를 기준으로 페이지리스트에 저장된 쿠폰들
     */
    Page<CouponReadResponseDto> findByCouponStatus_CouponStatusId(Integer couponStatusId,
        Pageable pageable);
}
