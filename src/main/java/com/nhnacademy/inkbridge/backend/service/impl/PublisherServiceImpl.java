package com.nhnacademy.inkbridge.backend.service.impl;

import static com.nhnacademy.inkbridge.backend.enums.PublisherEnum.PUBLISHER_NOT_FOUND;

import com.nhnacademy.inkbridge.backend.dto.PublisherResponseDto;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * class: PublisherServiceImpl.
 *
 * @author JBum
 * @version 2024/02/29
 */
@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    /**
     * publisherService 생성자
     *
     * @param publisherRepository publisherRepository자동 주입
     */
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    /**
     * {@inheritDoc}
     */
    public List<PublisherResponseDto> getPublisherList() {
        return publisherRepository.findAllBy();
    }

    /**
     * {@inheritDoc}
     */
    public PublisherResponseDto getPublisher(Long publisherId) {
        return publisherRepository.findByPublisherId(publisherId)
            .orElseThrow(() -> new NotFoundException(PUBLISHER_NOT_FOUND.getMessage()));
    }
}
