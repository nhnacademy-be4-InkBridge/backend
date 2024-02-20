package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: CategoryMessageEnum.
 *
 * @author JBum
 * @version 2024/02/20
 */
public enum CategoryMessageEnum {
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다.");
    private final String message;

    CategoryMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
