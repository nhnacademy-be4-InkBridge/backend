package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: CouponRepository.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponRepository extends JpaRepository<Coupon,String> {

}
