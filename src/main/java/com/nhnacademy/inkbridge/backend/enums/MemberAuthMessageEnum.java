package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 * class: MemberAuthMessageEnem.
 *
 * @author jeongbyeonghun
 * @version 2/21/24
 */
@Getter
public enum MemberAuthMessageEnum {
    MEMBER_AUTH_NOT_FOUND("맴버");

    private final String message;

    MemberAuthMessageEnum(String message) {
        this.message = message;
    }

}
