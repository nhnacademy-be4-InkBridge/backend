package com.nhnacademy.inkbridge.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * class: PayCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
@Getter
@NoArgsConstructor
@ToString
public class PayCreateRequestDto {

    private String payKey;
    private String orderCode;
    private Long totalAmount;
    private Long balanceAmount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime approvedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime requestedAt;
    private Long vat;
    private Boolean isPartialCancelable;
    private String method;
    private String status;
    private String provider;
}
