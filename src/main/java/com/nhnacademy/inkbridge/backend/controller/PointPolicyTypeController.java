package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyTypeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PointPolicyTypeRestController.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@RestController
@RequestMapping("/api/point-policy-types")
@RequiredArgsConstructor
public class PointPolicyTypeController {

    private final PointPolicyTypeService pointPolicyTypeService;

    /**
     * 포인트 정책 유형 전체 조회 메서드 입니다. <br/> 200의 상태코드로 전송됩니다.
     *
     * @return PointPolicyTypeReadResponseDto
     */
    @GetMapping
    public ResponseEntity<List<PointPolicyTypeReadResponseDto>> getPointPolicyTypes() {

        return ResponseEntity.ok(pointPolicyTypeService.getPointPolicyTypes());
    }

    /**
     * 포인트 정책 유형 생성 메서드 입니다. <br/> 생성 성공 시 201, 존재 할 경우 409, 유효성 검사 실패 시 400의 상태 코드가 전송됩니다.
     *
     * @param pointPolicyTypeCreateRequestDto PointPolicyTypeCreateRequestDto
     * @param bindingResult                   BindingResult
     */
    @PostMapping
    public ResponseEntity<Void> createPointPolicyType(
        @RequestBody @Valid PointPolicyTypeCreateRequestDto pointPolicyTypeCreateRequestDto,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                PointPolicyMessageEnum.POINT_POLICY_TYPE_VALID_FAIL.getMessage());
        }

        pointPolicyTypeService.createPointPolicyType(pointPolicyTypeCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 포인트 정책 유형 수정 메서드 입니다. <br/> 수정 성공시 200, 존재하지 않을 경우 404, 유효성 검사 실패 시 400의 상태 코드가 전송됩니다.
     *
     * @param pointPolicyTypeUpdateRequestDto PointPolicyTypeUpdateRequestDto
     * @param bindingResult                   BindingResult
     * @return PointPolicyTypeUpdateResponseDto
     */
    @PutMapping
    public ResponseEntity<Void> updatePointPolicyType(
        @RequestBody @Valid PointPolicyTypeUpdateRequestDto pointPolicyTypeUpdateRequestDto,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                PointPolicyMessageEnum.POINT_POLICY_TYPE_VALID_FAIL.getMessage());
        }

        pointPolicyTypeService.updatePointPolicyType(pointPolicyTypeUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 포인트 정책 유형 삭제 메서드 입니다. <br/> 삭제 성공시 200, 존재하지 않을 경우 404의 상태 코드가 전송됩니다.
     *
     * @param pointPolicyTypeId Integer
     * @return void
     */
    @DeleteMapping("/{pointPolicyTypeId}")
    public ResponseEntity<Void> deletePointPolicyType(@PathVariable Integer pointPolicyTypeId) {

        pointPolicyTypeService.deletePointPolicyTypeById(pointPolicyTypeId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
