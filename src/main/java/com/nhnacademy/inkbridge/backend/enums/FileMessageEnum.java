package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 * class: FileMessageEnum.
 *
 * @author jeongbyeonghun
 * @version 2/22/24
 */
@Getter
public enum FileMessageEnum {
    FILE_SAVE_ERROR("파일 저장 에러"), FILE_LOAD_ERROR("파일 불러오기 에러");

    private final String message;

    FileMessageEnum(String message) {
        this.message = message;
    }
}
