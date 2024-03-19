package com.nhnacademy.inkbridge.backend.dto.publisher;

import com.nhnacademy.inkbridge.backend.entity.Publisher;
import lombok.Getter;

/**
 * class: PublisherCreateRequestDto.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
@Getter
public class PublisherCreateRequestDto {

    private String publisherName;

    public static Publisher toPublisher(PublisherCreateRequestDto request) {
        return new Publisher(request.getPublisherName());
    }
}
