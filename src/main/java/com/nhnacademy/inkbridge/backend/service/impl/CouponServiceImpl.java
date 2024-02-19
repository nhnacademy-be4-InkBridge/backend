package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.coupon.IssueCouponRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.CouponRepository;
import com.nhnacademy.inkbridge.backend.repository.CouponTypeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.*;
import static com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum.*;

/**
 * class: CouponServiceImpl.
 *
 * @author ijeongbeom
 * @version 2024/02/15
 */
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponTypeRepository couponTypeRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private static final int COUPON_LENGTH = 10;

    public CouponServiceImpl(CouponRepository couponRepository,
        CouponTypeRepository couponTypeRepository, MemberRepository memberRepository,
        MemberCouponRepository memberCouponRepository) {
        this.couponRepository = couponRepository;
        this.couponTypeRepository = couponTypeRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    public void createCoupon(CouponCreateRequestDto couponCreateRequestDTO) {
        CouponType couponType = Optional.ofNullable(
                couponTypeRepository.getReferenceById(couponCreateRequestDTO.getCouponTypeId()))
            .orElseThrow(() -> new NotFoundException(
                String.format("%s%s%d", COUPON_TYPE_NOT_FOUND.getMessage(),
                    COUPON_TYPE_ID.getMessage(), couponCreateRequestDTO.getCouponTypeId())));
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

    public void issueCoupon(IssueCouponRequestDto issueCouponDto) {
        Coupon coupon = couponRepository.findById(issueCouponDto.getCouponId())
            .orElseThrow(() -> new NotFoundException(
                String.format("%s%s%d", COUPON_NOT_FOUND.getMessage(), COUPON_ID.getMessage(),
                    issueCouponDto.getCouponId())));
        Member member = memberRepository.findById(issueCouponDto.getMemberId())
            .orElseThrow(() -> new NotFoundException(
                String.format("%s%s%d", MEMBER_NOT_FOUND.getMessage(), MEMBER_ID.getMessage(),
                    issueCouponDto.getMemberId())));
        if (memberCouponRepository.existsByCouponAndMemberAnd(coupon, member)) {
            throw new AlreadyExistException(COUPON_ISSUED_EXIST.getMessage());
        }
        MemberCoupon memberCoupon = MemberCoupon.builder()
            .memberCouponId(generateCoupon())
            .member(member)
            .coupon(coupon)
            .issuedAt(LocalDateTime.now())
            .expiredAt(LocalDateTime.now().plusDays(coupon.getValidity()))
            .build();
        memberCouponRepository.saveAndFlush(memberCoupon);
    }


    public static String generateCoupon() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[COUPON_LENGTH / 2];
        secureRandom.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(16);
    }
}
