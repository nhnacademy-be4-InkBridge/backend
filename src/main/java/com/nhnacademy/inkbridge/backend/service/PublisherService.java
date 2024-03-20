package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: PublisherService.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
public interface PublisherService {

    void createPublisher(PublisherCreateRequestDto request);

    Page<PublisherReadResponseDto> readPublishers(Pageable pageable);

    void updatePublisher(Long publisherId, PublisherUpdateRequestDto publisherUpdateRequestDto);
}
