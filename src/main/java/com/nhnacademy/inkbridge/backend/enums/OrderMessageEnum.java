package com.nhnacademy.inkbridge.backend.enums;

import lombok.RequiredArgsConstructor;

/**
 * class: OrderMessageEnum.
 *
 * @author JBum
 * @version 2024/02/28
 */

@RequiredArgsConstructor
public enum OrderMessageEnum {
    WRAPPING_NOT_FOUND("포장지가 없습니다.");
    private String message;

    OrderMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}