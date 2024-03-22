package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PointHistoryController.
 *
 * @author jeongbyeonghun
 * @version 3/22/24
 */
@RestController
@RequestMapping("/api/mypage/pointHistory")
@RequiredArgsConstructor
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;

    @GetMapping
    ResponseEntity<List<PointHistoryReadResponseDto>> getPointHistory(
        @RequestHeader("Authorization-Id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(pointHistoryService.getPointHistory(userId));
    }
}
