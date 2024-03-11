package com.nhnacademy.inkbridge.backend.service;

/**
 * class: MemberPointService.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */
public interface MemberPointService {

    /**
     * 지정된 회원의 포인트를 업데이트합니다.
     *
     * @param memberId   회원의 ID
     * @param pointValue 변경할 포인트 값
     */
    void memberPointUpdate(Long memberId, Long pointValue);

    /**
     * 지정된 회원의 현재 포인트를 조회합니다.
     *
     * @param memberId 회원의 ID
     * @return 조회된 회원의 포인트
     */
    Long getMemberPoint(Long memberId);
}
