package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;
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
 * class: PointPolicyController.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@RestController
@RequestMapping("/api/point-policies")
@RequiredArgsConstructor
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;

    /**
     * 포인트 정책 전체 조회 메서드 입니다.
     *
     * @return PointPolicyReadResponseDto
     */
    @GetMapping
    public ResponseEntity<List<PointPolicyReadResponseDto>> getPointPolicies() {
        return ResponseEntity.ok(pointPolicyService.getPointPolicies());
    }

    /**
     * 포인트 정책 유형 id로 포인트 정책 내역을 조회하는 메소드 입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return List - PointPolicyReadResponseDto
     */
    @GetMapping("/{pointPolicyTypeId}")
    public ResponseEntity<List<PointPolicyReadResponseDto>> getPointPoliciesByTypeId(
        @PathVariable Integer pointPolicyTypeId) {

        return ResponseEntity.ok(pointPolicyService.getPointPoliciesByTypeId(pointPolicyTypeId));
    }

    /**
     * 등록된 모든 유형의 현재 적용중인 정책들을 조회하는 메소드입니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    @GetMapping("/current")
    public ResponseEntity<List<PointPolicyReadResponseDto>> getCurrentPointPolicies() {
        return ResponseEntity.ok(pointPolicyService.getCurrentPointPolicies());
    }

    /**
     * 포인트 정책 id에 맞는 유형의 현재 적용중인 정책을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    @GetMapping("/current/{pointPolicyTypeId}")
    public ResponseEntity<PointPolicyReadResponseDto> getCurrentPointPolicy(
        @PathVariable Integer pointPolicyTypeId) {
        return ResponseEntity.ok(pointPolicyService.getCurrentPointPolicy(pointPolicyTypeId));
    }


    /**
     * 포인트 정책 생성 메서드 입니다.
     *
     * @param pointPolicyCreateRequestDto PointPolicyCreateRequestDto
     * @param bindingResult               BindingResult
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> createPointPolicy(
        @RequestBody @Valid PointPolicyCreateRequestDto pointPolicyCreateRequestDto,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                PointPolicyMessageEnum.POINT_POLICY_VALID_FAIL.getMessage());
        }

        pointPolicyService.createPointPolicy(pointPolicyCreateRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
