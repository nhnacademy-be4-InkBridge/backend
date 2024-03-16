package com.nhnacademy.inkbridge.backend.dto.pay;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: PayReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@AllArgsConstructor
@Getter
public class PayReadResponseDto {

    private Long payId;

    private String paymentKey;

    private String method;

    private String status;

    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    private Long totalAmount;

    private Long balanceAmount;

    private Long vat;

    private Boolean isPartialCancelable;

    private String provider;
}
