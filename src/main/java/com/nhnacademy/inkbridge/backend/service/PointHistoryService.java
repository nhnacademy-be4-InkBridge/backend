package com.nhnacademy.inkbridge.backend.service;

/**
 * class: PointHistoryService.
 *
 * @author devminseo
 * @version 3/19/24
 */
public interface PointHistoryService {
    /**
     * 회원이 회원가입시 회원가입 축하금을 지급하는 메서드입니다.
     *
     * @param memberId 지급할 회원 아이디
     */
    void accumulatePointAtSignup(Long memberId);
}
