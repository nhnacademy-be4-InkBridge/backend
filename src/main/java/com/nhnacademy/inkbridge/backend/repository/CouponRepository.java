package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
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

    boolean existsByCouponName(String name);

    Page<CouponReadResponseDto> findByCouponType(CouponType couponType, Pageable pageable);

}
