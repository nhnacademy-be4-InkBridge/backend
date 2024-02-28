package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.PublisherResponseDto;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PublisherController.
 *
 * @author JBum
 * @version 2024/02/29
 */
@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * 출판사 생성자.
     *
     * @param publisherService 주입해줄 출판사 서비스
     */
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    /**
     * 모든 퍼블리셔를 조회하는 Api.
     *
     * @return 전체 publisher
     */
    @GetMapping
    public ResponseEntity<List<PublisherResponseDto>> getPublishers() {
        return ResponseEntity.ok(publisherService.getPublisherList());
    }

    /**
     * 특정 publisher를 조회하는 api
     *
     * @param publisherId 검색하고싶은 publisherId
     * @return publisher의 정보
     */
    @GetMapping("/{publisherId}")
    public ResponseEntity<PublisherResponseDto> getPublisher(
        @PathVariable("publisherId") Long publisherId) {
        return ResponseEntity.ok(publisherService.getPublisher(publisherId));
    }


}
