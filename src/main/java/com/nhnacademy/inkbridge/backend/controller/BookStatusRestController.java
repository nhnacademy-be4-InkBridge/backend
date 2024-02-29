package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookStatusService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: BookStatusRestController.
 *
 * @author minm063
 * @version 2024/02/27
 */
@RestController
public class BookStatusRestController {

    private final BookStatusService bookStatusService;

    public BookStatusRestController(BookStatusService bookStatusService) {
        this.bookStatusService = bookStatusService;
    }

    /**
     * BookStatus 전체 목록을 조회하는 메서드입니다.
     *
     * @return List - BookStatusReadResponseDto
     */
    @GetMapping("/api/admin/statuses")
    public ResponseEntity<List<BookStatusReadResponseDto>> getBookStatuses() {
        List<BookStatusReadResponseDto> statuses = bookStatusService.getStatuses();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
}
