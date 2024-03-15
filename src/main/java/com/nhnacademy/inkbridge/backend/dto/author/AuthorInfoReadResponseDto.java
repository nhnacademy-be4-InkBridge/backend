package com.nhnacademy.inkbridge.backend.dto.author;

import lombok.Builder;
import lombok.Getter;

/**
 * class: AuthorInfoReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/14
 */
@Getter
public class AuthorInfoReadResponseDto {

    private final String authorName;
    private final String authorIntroduce;
    private final String fileUrl;

    @Builder
    public AuthorInfoReadResponseDto(String authorName, String authorIntroduce, String fileUrl) {
        this.authorName = authorName;
        this.authorIntroduce = authorIntroduce;
        this.fileUrl = fileUrl;
    }
}
