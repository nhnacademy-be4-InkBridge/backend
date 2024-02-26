package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AdminCouponController.
 *
 * @author JBum
 * @version 2024/02/22
 */
@RestController
@RequestMapping("/api/admin/coupons")
public class AdminCouponController {

    private final CouponService couponService;

    public AdminCouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * admin이 관리를 위해 쿠폰리스트를 받아오는 메소드.
     *
     * @param couponStatusId 쿠폰상태
     * @param pageable       페이지, 사이즈
     * @return 쿠폰상태로 정리된 쿠폰리스트
     */
    @GetMapping
    public ResponseEntity<Page<CouponReadResponseDto>> getCoupons(
        @RequestParam(name = "coupon-status-id") int couponStatusId, Pageable pageable) {
        Page<CouponReadResponseDto> coupons = couponService.adminViewCoupons(pageable,
            couponStatusId);
        return ResponseEntity.ok(coupons);
    }

    /**
     * admin이 쿠폰을 생성하는 메소드.
     *
     * @param couponCreateRequestDto 쿠폰생성시 필요한 필드들
     * @param bindingResult          valid결과
     * @return 생성되었습니다
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCoupon(
        @Valid @RequestBody CouponCreateRequestDto couponCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(CouponMessageEnum.COUPON_VALIDATION_ERROR.getMessage());
        }
        couponService.createCoupon(couponCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
