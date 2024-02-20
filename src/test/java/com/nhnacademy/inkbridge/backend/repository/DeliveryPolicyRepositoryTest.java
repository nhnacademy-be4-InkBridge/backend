package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.DeliveryPolicy;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: DeliveryPolicyRepositoryTest.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class DeliveryPolicyRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DeliveryPolicyRepository deliveryPolicyRepository;

    DeliveryPolicy deliveryPolicy;

    @BeforeEach
    void setup() {
        deliveryPolicy = DeliveryPolicy.builder()
            .deliveryPrice(1000L)
            .createdAt(LocalDate.of(2024, 1, 1))
            .build();

        deliveryPolicy = entityManager.persist(deliveryPolicy);
    }

    @Test
    @DisplayName("배송비 정책 전체 조회")
    @Order(2)
    void testFindAllDeliveryPolicyBy() {
        List<DeliveryPolicyReadResponseDto> result = deliveryPolicyRepository.findAllDeliveryPolicyBy();

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(deliveryPolicy.getDeliveryPolicyId(),
                result.get(0).getDeliveryPolicyId()),
            () -> assertEquals(deliveryPolicy.getDeliveryPrice(), result.get(0).getDeliveryPrice()),
            () -> assertEquals(deliveryPolicy.getCreatedAt(), result.get(0).getCreatedAt())
        );
    }

    @Test
    @DisplayName("배송비 정책 id 조회")
    @Order(1)
    void testFindDeliveryPolicyId() {
        DeliveryPolicyReadResponseDto result = deliveryPolicyRepository.findDeliveryPolicyById(
            1L);

        assertAll(
            () -> assertEquals(deliveryPolicy.getDeliveryPolicyId(), result.getDeliveryPolicyId()),
            () -> assertEquals(deliveryPolicy.getDeliveryPrice(), result.getDeliveryPrice()),
            () -> assertEquals(deliveryPolicy.getCreatedAt(), result.getCreatedAt())
        );
    }

    @Test
    @DisplayName("현재 적용되는 배송비 정책 조회")
    @Order(3)
    void testFindCurrentPolicy() {
        DeliveryPolicy currentPolicy = DeliveryPolicy.builder()
            .deliveryPrice(1500L)
            .createdAt(LocalDate.of(2024, 1, 2))
            .build();

        entityManager.persist(currentPolicy);

        DeliveryPolicyReadResponseDto result = deliveryPolicyRepository.findCurrentPolicy();

        assertAll(
            () -> assertEquals(currentPolicy.getDeliveryPolicyId(), result.getDeliveryPolicyId()),
            () -> assertEquals(currentPolicy.getDeliveryPrice(), result.getDeliveryPrice()),
            () -> assertEquals(currentPolicy.getCreatedAt(), result.getCreatedAt())
        );
    }
}