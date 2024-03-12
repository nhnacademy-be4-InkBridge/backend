package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.WrappingService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity createWrapping(
        @RequestBody @Valid WrappingCreateRequestDto wrappingCreateRequestDto) {
        wrappingService.createWrapping(wrappingCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
