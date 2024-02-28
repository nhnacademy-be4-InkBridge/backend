package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.PublisherResponseDto;
import java.util.List;

/**
 * class: PublisherService.
 *
 * @author JBum
 * @version 2024/02/29
 */
public interface PublisherService {

    /**
     * 전체 출판사 조회
     *
     * @return 전체 출판사 정보
     */
    List<PublisherResponseDto> getPublisherList();

    /**
     * 출판사 한곳만 조회
     *
     * @param publisherId 조회할 출판사 id
     * @return 출판사 정보
     */
    PublisherResponseDto getPublisher(Long publisherId);
}
