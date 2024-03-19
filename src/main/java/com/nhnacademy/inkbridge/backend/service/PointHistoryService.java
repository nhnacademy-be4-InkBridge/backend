package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.entity.Member;

/**
 * class: PointHistoryService.
 *
 * @author devminseo
 * @version 3/19/24
 */
public interface PointHistoryService {
    void accumulatePointAtSignup(Member member);
}
