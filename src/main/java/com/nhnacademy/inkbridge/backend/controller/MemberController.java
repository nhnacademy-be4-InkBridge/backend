package com.nhnacademy.inkbridge.backend.controller;

import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_TYPE_NOT_FOUND;

import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.OrderCouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.enums.MemberCouponStatusEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: MemberController.
 *
 * @author minseo
 * @version 2/15/24
 * @modifiedBy JBum
 * @modifiedAt 3/7/24
 * @modificationReason - getOrderCoupons 추가, getMemberCoupons 추가
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final CouponService couponService;

    /**
     * 회원가입 하는 메서드입니다.
     *
     * @param memberCreateRequestDto 회원가입 폼 데이터
     * @return 회원가입 성공
     */
    @PostMapping("/members")
    public ResponseEntity<HttpStatus> create(
        @RequestBody @Valid MemberCreateRequestDto memberCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(MemberMessageEnum.MEMBER_VALID_FAIL.getMessage());
        }

        memberService.createMember(memberCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/members/login")
    public ResponseEntity<MemberAuthLoginResponseDto> authLogin(
        @RequestBody @Valid MemberAuthLoginRequestDto memberAuthLoginRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.toString());
        }
        MemberAuthLoginResponseDto memberAuthLoginResponseDto =
            memberService.loginInfoMember(memberAuthLoginRequestDto);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(memberAuthLoginResponseDto);
    }

    @GetMapping("/auth/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(HttpServletRequest request) {

        Long memberId = Long.parseLong(request.getHeader("Authorization-Id"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(memberService.getMemberInfo(memberId));
    }

    @GetMapping("/auth/members/{memberId}/order-coupons")
    public ResponseEntity<List<OrderCouponReadResponseDto>> getOrderCoupons(
        @PathVariable("memberId") Long memberId, @RequestParam("book-id") Long[] bookId) {
        return ResponseEntity.ok(couponService.getOrderCouponList(bookId, memberId));
    }

    @GetMapping("/auth/members/{memberId}/coupons")
    public ResponseEntity<List<MemberCouponReadResponseDto>> getMemberCoupons(
        @PathVariable("memberId") Long memberId,
        @RequestParam(value = "status", defaultValue = "ACTIVE") String status) {
        MemberCouponStatusEnum statusEnum;
        try {
            statusEnum = MemberCouponStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NotFoundException(COUPON_TYPE_NOT_FOUND.getMessage());
        }
        return ResponseEntity.ok(
            couponService.getMemberCouponList(memberId, statusEnum
            ));
    }

    @PostMapping("/auth/members/{memberId}/coupons/{couponId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity issueCoupon(@PathVariable("memberId") Long memberId,
        @PathVariable("couponId") String couponId) {
        couponService.issueCoupon(memberId, couponId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/oauth/check")
    public ResponseEntity<Boolean> oauthMemberCheck(@RequestBody MemberIdNoRequestDto memberIdNoRequestDto) {
        boolean result = memberService.checkOAuthMember(memberIdNoRequestDto.getId());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/oauth")
    public ResponseEntity<String> getOAuthEmail(@RequestBody MemberIdNoRequestDto memberIdNoRequestDto) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(memberService.getOAuthMemberEmail(memberIdNoRequestDto.getId()));
    }
}
