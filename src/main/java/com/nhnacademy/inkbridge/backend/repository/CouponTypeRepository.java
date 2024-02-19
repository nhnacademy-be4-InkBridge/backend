package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: CouponTypeRepository.
 *
 * @author ijeongbeom
 * @version 2024/02/19
 */
public interface CouponTypeRepository extends JpaRepository<CouponType,Integer> {

}
