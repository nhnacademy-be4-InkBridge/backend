package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 * class: MemberStatusMessageEnum.
 *
 * @author jeongbyeonghun
 * @version 2/21/24
 */
@Getter
public enum MemberStatusMessageEnum {

    MEMBER_STATUS_NOT_FOUND("회원 상태를 찾을 수 없습니다.");

    private final String message;

    MemberStatusMessageEnum(String message) {
        this.message = message;
    }
}
