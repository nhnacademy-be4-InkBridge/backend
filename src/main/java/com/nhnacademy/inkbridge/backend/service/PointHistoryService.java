package com.nhnacademy.inkbridge.backend.service;

/**
 * class: PointHistoryService.
 *
 * @author devminseo
 * @version 3/19/24
 */
public interface PointHistoryService {
    void accumulatePointAtSignup(Long memberId);
}
