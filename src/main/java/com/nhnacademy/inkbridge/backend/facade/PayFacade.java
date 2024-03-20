package com.nhnacademy.inkbridge.backend.facade;

import com.nhnacademy.inkbridge.backend.dto.OrderedMemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
import com.nhnacademy.inkbridge.backend.service.BookOrderDetailService;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import com.nhnacademy.inkbridge.backend.service.BookService;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import com.nhnacademy.inkbridge.backend.service.PayService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: PayPacade.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PayFacade {

    private final PayService payService;
    private final BookOrderService bookOrderService;
    private final BookOrderDetailService bookOrderDetailService;
    private final CouponService couponService;
    private final MemberPointService memberPointService;
    private final AccumulationRatePolicyService accumulationRatePolicyService;

    /**
     * 결제 정보를 저장하고 결제를 진행합니다.
     *
     * @param requestDto 결제 정보
     */
    public void doPay(PayCreateRequestDto requestDto) {

        payService.createPay(requestDto);

        bookOrderService.updateBookOrderPayStatusByOrderCode(requestDto.getOrderCode());

        OrderedMemberPointReadResponseDto orderedResponseDto = bookOrderService.getOrderedPersonByOrderCode(
            requestDto.getOrderCode());
        if (Objects.nonNull(orderedResponseDto.getMemberId())) {

            memberPointService.memberPointUpdate(orderedResponseDto.getMemberId(),
                orderedResponseDto.getUsePoint() * -1);

            Integer rate = accumulationRatePolicyService.getCurrentAccumulationRate();
            memberPointService.memberPointUpdate(orderedResponseDto.getMemberId(),
                Math.round((double) orderedResponseDto.getTotalPrice() * rate) / 100);

            List<Long> usedCouponIdList = bookOrderDetailService.getUsedCouponIdByOrderCode(
                requestDto.getOrderCode());
            couponService.useCoupons(orderedResponseDto.getMemberId(), usedCouponIdList);
        }
    }


}
