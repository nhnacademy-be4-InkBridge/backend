package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * class: PublisherServiceImpl.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public void createPublisher(PublisherCreateRequestDto request) {
        Publisher publisher = PublisherCreateRequestDto.toPublisher(request);
        publisherRepository.save(publisher);
    }

    @Override
    public Page<PublisherReadResponseDto> readPublishers(Pageable pageable) {
        return publisherRepository.findAllBy(pageable)
            .map(PublisherReadResponseDto::toPublisherReadResponseDto);
    }
}
