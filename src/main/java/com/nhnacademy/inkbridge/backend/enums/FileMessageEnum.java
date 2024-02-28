package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: FileMessageEnem.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */

@RequiredArgsConstructor
@Getter
public enum FileMessageEnum {
    FILE_NOT_FOUND_ERROR("파일을 찾지 못했습니다.");

    private final String message;
}
