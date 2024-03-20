package com.nhnacademy.inkbridge.backend.dto.publisher;

import com.nhnacademy.inkbridge.backend.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: PublisherReadResponseDto.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
@Getter
@AllArgsConstructor
public class PublisherReadResponseDto {
    private Long publisherId;
    private String publisherName;

    public static PublisherReadResponseDto toPublisherReadResponseDto(Publisher publisher){
        return new PublisherReadResponseDto(publisher.getPublisherId(),publisher.getPublisherName());
    }
}
