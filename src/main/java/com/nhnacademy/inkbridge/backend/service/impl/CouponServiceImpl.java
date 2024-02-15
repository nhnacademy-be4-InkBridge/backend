package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.repository.CouponRepository;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;

/**
 * class: CouponServiceImpl.
 *
 * @author ijeongbeom
 * @version 2024/02/15
 */
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private static final int COUPON_LENGTH = 10;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void createCoupon(CouponCreateRequestDto couponCreateRequestDTO) {
        CouponType couponType = CouponType.builder()
            .couponTypeId(couponCreateRequestDTO.getCouponTypeId()).
            build();
        Coupon newCoupon = Coupon.builder()
            .couponId(generateCoupon())
            .couponType(couponType)
            .couponName(couponCreateRequestDTO.getCouponName())
            .basicExpiredDate(couponCreateRequestDTO.getBasicExpiredDate())
            .basicIssuedDate(couponCreateRequestDTO.getBasicIssuedDate())
            .discountPrice(couponCreateRequestDTO.getDiscountPrice())
            .maxDiscountPrice(couponCreateRequestDTO.getMaxDiscountPrice())
            .minPrice(couponCreateRequestDTO.getMinPrice())
            .isBirth(couponCreateRequestDTO.getIsBirth())
            .validity(couponCreateRequestDTO.getValidity())
            .build();
        couponRepository.saveAndFlush(newCoupon);
    }


    public static String generateCoupon() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[COUPON_LENGTH / 2];
        secureRandom.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(16);
    }
}
