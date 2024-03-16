package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pay.PayReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.entity.Pay;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.PayMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.PayRepository;
import com.nhnacademy.inkbridge.backend.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * class: PayServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;
    private final BookOrderRepository bookOrderRepository;

    /**
     * 결제 정보를 저장하는 메소드입니다.
     *
     * @param requestDto 결제정보
     */
    @Override
    public void createPay(PayCreateRequestDto requestDto) {
        BookOrder bookOrder = bookOrderRepository.findByOrderCode(requestDto.getOrderCode())
            .orElseThrow(() -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage()));

        Pay pay = Pay.builder()
            .paymentKey(requestDto.getPayKey())
            .order(bookOrder)
            .totalAmount(requestDto.getTotalAmount())
            .balanceAmount(requestDto.getBalanceAmount())
            .approvedAt(requestDto.getApprovedAt())
            .requestedAt(requestDto.getRequestedAt())
            .vat(requestDto.getVat())
            .method(requestDto.getMethod())
            .status(requestDto.getStatus())
            .provider(requestDto.getProvider())
            .build();

        payRepository.save(pay);
    }

    @Override
    public PayReadResponseDto getPayByPayId(Long payId) {
        return payRepository.findPayByPayId(payId)
            .orElseThrow(() -> new NotFoundException(PayMessageEnum.PAY_NOT_FOUND.getMessage()));
    }

    @Override
    public PayReadResponseDto getPayByOrderId(Long orderId) {
        return payRepository.findPayByOrderId(orderId)
            .orElseThrow(() -> new NotFoundException(PayMessageEnum.PAY_NOT_FOUND.getMessage()));
    }

    @Override
    public void cancelPay(Long payId, Long cancelAmount) {

    }
}
