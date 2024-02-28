package com.nhnacademy.inkbridge.backend.dto;

import lombok.Getter;

/**
 * class: PublisherResponseDto.
 *
 * @author JBum
 * @version 2024/02/29
 */
@Getter
public class PublisherResponseDto {

    private Long publisherId;

    private String publisherName;

    public PublisherResponseDto(Long publisherId, String publisherName) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }
}
