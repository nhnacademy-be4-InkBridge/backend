package com.nhnacademy.inkbridge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: OrderedMemberReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@AllArgsConstructor
@Getter
public class OrderedMemberPointReadResponseDto {

    private Long memberId;
    private Long usePoint;
}
