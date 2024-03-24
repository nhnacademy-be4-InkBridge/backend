package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.PointHistoryRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: PointHistoryServiceImplTest.
 *
 * @author jeongbyeonghun
 * @version 3/24/24
 */

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceImplTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointHistoryServiceImpl pointHistoryService;

    private static List<PointHistoryReadResponseDto> testList;
    private static String reason;
    private static Long point;
    private static LocalDateTime accruedAt;
    private static Member member;

    @BeforeAll
    static void setUp() {
        testList = new ArrayList<>();
        reason = "test";
        point = 100L;
        accruedAt = LocalDateTime.now();
        testList.add(new PointHistoryReadResponseDto(reason, point, accruedAt));
        member = Member.create().build();
    }

    @Test
    void getPointHistory() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(pointHistoryRepository.findByMemberOrderByAccruedAtDesc(member)).thenReturn(testList);
        List<PointHistoryReadResponseDto> result = pointHistoryService.getPointHistory(1L);

        assertEquals(reason, result.get(0).getReason());
        assertEquals(point, result.get(0).getPoint());
        assertEquals(accruedAt, result.get(0).getAccruedAt());
        assertEquals(testList.size(), result.size());
    }
    @Test
    void getPointHistoryWhenMemberNotFound() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pointHistoryService.getPointHistory(1L));
    }
}