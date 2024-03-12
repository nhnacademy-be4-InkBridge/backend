package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingCreateRequestDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.WrappingService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AdminWrappingController.
 *
 * @author JBum
 * @version 2024/03/12
 */

@RestController
@RequestMapping("/api/admin/wrappings")
public class AdminWrappingController {

    private final WrappingService wrappingService;

    public AdminWrappingController(WrappingService wrappingService) {
        this.wrappingService = wrappingService;
    }

    /**
     * 새로운 포장지 추가 메소드.
     *
     * @param wrappingCreateRequestDto 새로운 포장지 정보
     * @param bindingResult            입력값의 대한 조건
     * @return 201 OK
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createWrapping(
        @RequestBody @Valid WrappingCreateRequestDto wrappingCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
        wrappingService.createWrapping(wrappingCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 기존 포장지 수정 메소드
     *
     * @param wrappingId               수정하고싶은 포장지 Id
     * @param wrappingCreateRequestDto 수정하고싶은 내용
     * @param bindingResult            입력값의 대한 조건
     * @return 200 OK
     */
    @PutMapping("/{wrappingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateWrapping(@PathVariable("wrappingId") Long wrappingId,
        @RequestBody @Valid WrappingCreateRequestDto wrappingCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
        wrappingService.updateWrapping(wrappingId, wrappingCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
