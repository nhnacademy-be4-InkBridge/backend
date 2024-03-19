package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.member.MemberPointReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: MemberPointController.
 *
 * @author jeongbyeonghun
 * @version 3/19/24
 */

@RestController
@RequestMapping("/api/mypage/point")
@RequiredArgsConstructor
public class MemberPointController {

    private final MemberPointService memberPointService;

    @GetMapping
    ResponseEntity<MemberPointReadResponseDto> getPoint(
        @RequestHeader("Authorization-Id") Long userId) {
        return ResponseEntity.ok(MemberPointReadResponseDto.builder().point(
            memberPointService.getMemberPoint(userId)).build());
    }
}
