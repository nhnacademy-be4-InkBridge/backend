package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * class: CouponRepositoryTest.
 *
 * @author JBum
 * @version 2024/02/20
 */
@ActiveProfiles("local")
@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponTypeRepository couponTypeRepository;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        // 쿠폰 타입 저장
        CouponType couponType = new CouponType(1, "test");
        couponTypeRepository.save(couponType);

        // 쿠폰 생성
        coupon = Coupon.builder()
            .couponId("ABC1234")
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.of(2024, 2, 20))
            .basicExpiredDate(LocalDate.of(2024, 12, 31))
            .validity(30)
            .isBirth(true)
            .couponType(couponType)
            .build();
        couponRepository.save(coupon);
    }

    @Test
    void saveCouponSuccess() {
        //Given
        Coupon couponTest = Coupon.builder()
            .couponId("ABC123")
            .couponName("Test Coupon")
            .minPrice(10000L)
            .maxDiscountPrice(5000L)
            .discountPrice(2000L)
            .basicIssuedDate(LocalDate.of(2024, 2, 20))
            .basicExpiredDate(LocalDate.of(2024, 12, 31))
            .validity(30)
            .isBirth(true)
            .couponType(CouponType.builder().couponTypeId(1).build())
            .build();

        // When
        Coupon savedCoupon = couponRepository.save(couponTest);

        // Then
        Optional<Coupon> retrievedCoupon = couponRepository.findById(savedCoupon.getCouponId());
        assertTrue(retrievedCoupon.isPresent());
        assertEquals(savedCoupon.getCouponName(), retrievedCoupon.get().getCouponName());
    }

    @Test
    void updateCouponSuccess() {
        // Given
        String newCouponName = "Updated Coupon Name";
        Long newMinPrice = 15000L;

        // When
        Coupon existingCoupon = couponRepository.findById("ABC1234").orElseThrow();
        Coupon updatedCoupon = Coupon.builder()
            .couponId(existingCoupon.getCouponId())
            .couponName(newCouponName)
            .minPrice(newMinPrice)
            .maxDiscountPrice(existingCoupon.getMaxDiscountPrice())
            .discountPrice(existingCoupon.getDiscountPrice())
            .basicIssuedDate(existingCoupon.getBasicIssuedDate())
            .basicExpiredDate(existingCoupon.getBasicExpiredDate())
            .validity(existingCoupon.getValidity())
            .isBirth(existingCoupon.getIsBirth())
            .couponType(existingCoupon.getCouponType())
            .build();
        couponRepository.save(updatedCoupon);

        // Then
        Coupon retrievedCoupon = couponRepository.findById("ABC1234").orElseThrow();
        assertEquals(newCouponName, retrievedCoupon.getCouponName());
        assertEquals(newMinPrice, retrievedCoupon.getMinPrice());
    }
//    @Test
//    void deleteCoupon() {
//        // Given
//        Coupon coupon = new Coupon();
//        coupon.setCouponName("TestCoupon");
//        coupon.setBasicExpiredDate(LocalDate.now().plusMonths(1));
//        coupon.setBasicIssuedDate(LocalDate.now());
//        couponRepository.save(coupon);
//
//        // When
//        couponRepository.deleteByCouponName("TestCoupon");
//
//        // Then
//        assertFalse(couponRepository.existsByCouponName("TestCoupon"));
//    }
//
//    @Test
//    void existsByCouponName() {
//        // Given
//        Coupon coupon = new Coupon();
//        coupon.setCouponName("TestCoupon");
//        coupon.setBasicExpiredDate(LocalDate.now().plusMonths(1));
//        coupon.setBasicIssuedDate(LocalDate.now());
//        couponRepository.save(coupon);
//
//        // When
//        boolean exists = couponRepository.existsByCouponName("TestCoupon");
//
//        // Then
//        assertTrue(exists);
//    }
}