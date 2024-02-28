package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: PublisherEnum.
 *
 * @author JBum
 * @version 2024/02/29
 */
public enum PublisherEnum {
    PUBLISHER_NOT_FOUND("출판사가 존재하지 않습니다.");

    private final String message;

    PublisherEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
