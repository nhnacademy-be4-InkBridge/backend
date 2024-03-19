package com.nhnacademy.inkbridge.backend.entity.enums;

/**
 * class: PointHistoryReason.
 *
 * @author devminseo
 * @version 3/19/24
 */
public enum PointHistoryReason {
    REGISTER_MSG("회원가입 축하금 지급");

    private final String message;

    PointHistoryReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
