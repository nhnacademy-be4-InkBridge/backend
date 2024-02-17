package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: PointPolicyRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
@DataJpaTest
class PointPolicyRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PointPolicyRepository pointPolicyRepository;
    PointPolicy pointPolicy;

    @BeforeEach
    void setup() {
        PointPolicyType pointPolicyType = PointPolicyType.builder()
            .pointPolicyTypeId(1)
            .policyType("REGISTER")
            .build();

        entityManager.persist(pointPolicyType);

        pointPolicy = PointPolicy.builder()
            .accumulatePoint(1000L)
            .createdAt(LocalDate.of(2024, 1, 1))
            .pointPolicyType(pointPolicyType)
            .build();

        pointPolicy = entityManager.persist(pointPolicy);
    }

    @Test
    @DisplayName("포인트 정책 전체 조회 테스트")
    void testFindAllPointPolicyBy() {
        List<PointPolicyReadResponseDto> responseDtoList = pointPolicyRepository.findAllPointPolicyBy();

        assertAll(
            () -> assertEquals(1, responseDtoList.size()),
            () -> assertEquals(pointPolicy.getPointPolicyId(),
                responseDtoList.get(0).getPointPolicyId()),
            () -> assertEquals(pointPolicy.getPointPolicyType().getPolicyType(),
                responseDtoList.get(0).getPolicyType()),
            () -> assertEquals(pointPolicy.getAccumulatePoint(),
                responseDtoList.get(0).getAccumulatePoint()),
            () -> assertEquals(pointPolicy.getCreatedAt(), responseDtoList.get(0).getCreatedAt())
        );
    }

}