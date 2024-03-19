package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;

/**
 * class: PublisherService.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
public interface PublisherService {

    void createPublisher(PublisherCreateRequestDto request);
}
