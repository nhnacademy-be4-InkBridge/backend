package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.DeliveryPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: DeliveryPolicyController.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@RestController
@RequestMapping("/api/delivery-policies")
@RequiredArgsConstructor
public class DeliveryPolicyController {

    private final DeliveryPolicyService deliveryPolicyService;

    /**
     * 배송비 정책을 전체 조회하는 메소드 입니다.
     *
     * @return List - DeliveryPolicyReadResponseDto
     */
    @GetMapping
    public ResponseEntity<List<DeliveryPolicyReadResponseDto>> getDeliveryPolicies() {
        return ResponseEntity.ok(deliveryPolicyService.getDeliveryPolicies());
    }

    /**
     * 배송비 정책 id를 통해 배송비 정책을 조회하는 메소드 입니다. <br/> 조회 성공시 200, 조회 실패 시 404의 상태코드가 전송됩니다.
     *
     * @param deliveryPolicyId Long
     * @return DeliveryPolicyReadResponseDto
     */
    @GetMapping("/{deliveryPolicyId}")
    public ResponseEntity<DeliveryPolicyReadResponseDto> getDeliveryPolicyById(
        @PathVariable Long deliveryPolicyId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(deliveryPolicyService.getDeliveryPolicy(deliveryPolicyId));
    }

    /**
     * 현재 적용 배송비 정책을 조회하는 메소드입니다.
     *
     * @return DeliveryPolicyReadResponseDto
     */
    @GetMapping("/current")
    public ResponseEntity<DeliveryPolicyReadResponseDto> getCurrentDeliveryPolicy() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(deliveryPolicyService.getCurrentDeliveryPolicy());
    }

    /**
     * 배송비 정책을 등록하는 메소드 입니다. <br/> 생성 성공시 201, 유효성 검사 실패 시 422의 상태코드가 전송됩니다.
     *
     * @param deliveryPolicyCreateRequestDto DeliveryPolicyCreateRequestDto
     * @param bindingResult                  BindingResult
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> createDeliveryPolicy(
        @RequestBody @Valid DeliveryPolicyCreateRequestDto deliveryPolicyCreateRequestDto,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                DeliveryPolicyMessageEnum.DELIVERY_VALID_FAIL.getMessage());
        }

        deliveryPolicyService.createDeliveryPolicy(deliveryPolicyCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
