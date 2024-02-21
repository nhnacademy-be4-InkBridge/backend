package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 * class: MemberGradeMessageEnum.
 *
 * @author jeongbyeonghun
 * @version 2/21/24
 */
@Getter
public enum MemberGradeMessageEnum {
    MEMBER_GRADE_NOT_FOUND("회원 등급을 찯을 수 없습니다.");

    private final String message;

    MemberGradeMessageEnum(String message) {
        this.message = message;
    }
}
