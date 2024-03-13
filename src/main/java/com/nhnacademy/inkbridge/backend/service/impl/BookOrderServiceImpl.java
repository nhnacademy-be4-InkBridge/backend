package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateRequestDto.BookOrderCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.OrderCreateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.service.BookOrderService;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: BookOrderServiceImpl.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookOrderServiceImpl implements BookOrderService {

    private final BookOrderRepository bookOrderRepository;
    private final MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     *
     * @param requestDto 주문 정보
     * @return 주문 번호
     */
    @Override
    public OrderCreateResponseDto createBookOrder(BookOrderCreateRequestDto requestDto) {

        BookOrder bookOrder = BookOrder.builder()
            .orderId(UUID.randomUUID().toString().replace("-", ""))
            .orderName(requestDto.getOrderName())
            .orderAt(LocalDateTime.now())
            .receiver(requestDto.getReceiverName())
            .receiverNumber(requestDto.getReceiverPhoneNumber())
            .zipCode(requestDto.getZipCode())
            .address(requestDto.getAddress())
            .addressDetail(requestDto.getDetailAddress())
            .orderer(requestDto.getSenderName())
            .ordererNumber(requestDto.getSenderPhoneNumber())
            .ordererEmail(requestDto.getSenderEmail())
            .deliveryDate(requestDto.getDeliveryDate())
            .usePoint(requestDto.getUsePoint())
            .totalPrice(requestDto.getPayAmount())
            .deliveryPrice(requestDto.getDeliveryPrice())
            .isPayment(false)
            .member(Objects.nonNull(requestDto.getMemberId()) ?
                memberRepository.findById(requestDto.getMemberId())
                    .orElseThrow(() -> new NotFoundException(
                        MemberMessageEnum.MEMBER_NOT_FOUND.getMessage())) : null)
            .build();

        bookOrder = bookOrderRepository.save(bookOrder);

        return new OrderCreateResponseDto(bookOrder.getOrderId());
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId 주문 번호
     * @return 주문 결제 정보
     */
    @Override
    public OrderPayInfoReadResponseDto getOrderPaymentInfoByOrderId(String orderId) {
        return bookOrderRepository.findOrderPayByOrderId(orderId).orElseThrow(
            () -> new NotFoundException(OrderMessageEnum.ORDER_NOT_FOUND.getMessage())
        );
    }

}
