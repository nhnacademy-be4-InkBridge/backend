package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookCreateResponseDto.
 *
 * @author minm063
 * @version 2024/02/14
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCreateResponseDto {
    String message;
}
