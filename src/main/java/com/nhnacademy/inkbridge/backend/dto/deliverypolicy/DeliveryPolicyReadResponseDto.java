package com.nhnacademy.inkbridge.backend.dto.deliverypolicy;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: DeliveryPolicyReadRequestDto.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@Getter
@RequiredArgsConstructor
public class DeliveryPolicyReadResponseDto {
    private final Long deliveryPolicyId;
    private final Long deliveryPrice;
    private final LocalDate createdAt;
    private final Long freeDeliveryPrice;
}
