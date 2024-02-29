package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.coupon.BookCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CategoryCouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponIssueRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.AlreadyUsedException;
import com.nhnacademy.inkbridge.backend.exception.InvalidPeriodException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: CouponService.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponService {

    /**
     * 관리자가 쿠폰을 생성하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param couponCreateRequestDto 쿠폰을 생성하기 위한 Request DTO
     * @throws NotFoundException    입력된 쿠폰 타입이 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 카테고리가 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 책이 존재하지 않는 경우 예외 발생
     * @throws AlreadyUsedException 존재하지 않는 쿠폰타입이면 예외 발생
     */
    void createCoupon(CouponCreateRequestDto couponCreateRequestDto);

    /**
     * 사용자가 쿠폰을 등록하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param issueCouponDto 쿠폰을 등록하기 위한 Request DTO
     * @throws NotFoundException      존재하지 않는 쿠폰이 입력된 경우 예외 발생
     * @throws AlreadyExistException  이미 등록된 쿠폰인 경우 예외 발생
     * @throws InvalidPeriodException 쿠폰 발급이 가능한 날짜가 아닌 경우 예외 발생
     */
    void issueCoupon(CouponIssueRequestDto issueCouponDto);

    /**
     * 관리자용 쿠폰리스트를 보여주는 메소드.
     *
     * @param pageable       페이지
     * @param couponStatusId 쿠폰상태별로 보기 위한 파라미터
     * @return 쿠폰리스트
     * @throws NotFoundException 존재하는 쿠폰상태가 아닌 경우 예외 발생
     */
    Page<CouponReadResponseDto> adminViewCoupons(Pageable pageable, int couponStatusId);

    /**
     * 관리자가 책전용 쿠폰을 생성하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param bookCouponCreateRequestDto 책전용 쿠폰을 생성하기 위한 Request DTO
     * @throws NotFoundException    입력된 쿠폰 타입이 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 카테고리가 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 책이 존재하지 않는 경우 예외 발생
     * @throws AlreadyUsedException 존재하지 않는 쿠폰타입이면 예외 발생
     */
    void createBookCoupon(BookCouponCreateRequestDto bookCouponCreateRequestDto);

    /**
     * 관리자가 카테고리전용 쿠폰을 생성하는 메소드. UUID에 randomUUID기능을 사용하여 난수의 중복을 방지한다.
     *
     * @param categoryCouponCreateRequestDto 쿠폰전용 쿠폰을 생성하기 위한 Request DTO
     * @throws NotFoundException    입력된 쿠폰 타입이 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 카테고리가 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 책이 존재하지 않는 경우 예외 발생
     * @throws AlreadyUsedException 존재하지 않는 쿠폰타입이면 예외 발생
     */
    void createCategoryCoupon(
        CategoryCouponCreateRequestDto categoryCouponCreateRequestDto);

}
