package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: BookMessageEnum.
 *
 * @author minm063
 * @version 2024/02/15
 */
public enum BookMessageEnum {
    BOOK_NOT_FOUND("책를 찾을 수 없습니다.");
    private final String message;

    BookMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}