package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberPointMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: MemberPointServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */
@Service
@RequiredArgsConstructor
public class MemberPointServiceImpl implements MemberPointService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void memberPointUpdate(Long memberId, Long pointValue) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        member.setMemberPoint(member.getMemberPoint() + pointValue);
        if(member.getMemberPoint() < 0) throw new ValidationException(
            MemberPointMessageEnum.MEMBER_POINT_VALID_FAIL.getMessage());
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public Long getMemberPoint(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        return member.getMemberPoint();
    }
}
