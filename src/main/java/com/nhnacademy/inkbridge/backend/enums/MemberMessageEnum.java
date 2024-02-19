package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: MemberMessageEnum.
 *
 * @author minm063
 * @version 2024/02/15
 */
public enum MemberMessageEnum {
    MEMBER_NOT_FOUND("요청하신 맴버를 찾을 수 없습니다."),
    MEMBER_ID("MemberId: ");

    private final String message;

    MemberMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
