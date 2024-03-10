package com.nhnacademy.inkbridge.backend.service;

/**
 * class: MemberPointService.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */
public interface MemberPointService {
    void memberPointUpdate(Long memberId, Long pointValue);

    Long getMemberPoint(Long memberId);
}
