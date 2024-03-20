package com.nhnacademy.inkbridge.backend.dto.publisher;

import com.nhnacademy.inkbridge.backend.entity.Publisher;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: PublisherCreateRequestDto.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
@Getter
@EqualsAndHashCode
public class PublisherCreateRequestDto {

    @NotBlank
    @Size(min = 1, max = 30)
    private String publisherName;

    public static Publisher toPublisher(PublisherCreateRequestDto request) {
        return new Publisher(request.getPublisherName());
    }
}
