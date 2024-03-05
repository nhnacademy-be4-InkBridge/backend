package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.repository.CouponRepository;
import com.nhnacademy.inkbridge.backend.repository.CouponTypeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: CouponServiceImplTest.
 *
 * @author JBum
 * @version 2024/02/19
 */

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private CouponTypeRepository couponTypeRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberCouponRepository memberCouponRepository;
    private CouponCreateRequestDto couponCreateRequestDto;


    @Test
    void createCoupon() {

    }

    @Test
    void issueCoupon() {
    }
}