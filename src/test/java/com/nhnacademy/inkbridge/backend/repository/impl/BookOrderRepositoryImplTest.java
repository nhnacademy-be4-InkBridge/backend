package com.nhnacademy.inkbridge.backend.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.inkbridge.backend.dto.OrderPayInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import com.nhnacademy.inkbridge.backend.repository.BookOrderRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * class: BookOrderRepositoryImplTest.
 *
 * @author jangjaehun
 * @version 2024/03/13
 */
@DataJpaTest
class BookOrderRepositoryImplTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookOrderRepository bookOrderRepository;

    BookOrder bookOrder;

    @BeforeEach
    void setup() {
        bookOrder = BookOrder.builder()
            .orderId("orderId")
            .orderName("orderName")
            .orderAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .receiver("receiverName")
            .receiverNumber("01011112222")
            .zipCode("00000")
            .address("address")
            .addressDetail("addressDetail")
            .orderer("senderName")
            .ordererNumber("01022223333")
            .ordererEmail("sender@inkbridge.store")
            .deliveryDate(LocalDate.of(2024, 1, 1))
            .usePoint(0L)
            .totalPrice(10000L)
            .deliveryPrice(3000L)
            .isPayment(false)
            .build();

        entityManager.persist(bookOrder);
    }

    @Test
    void testFindOrderPayByOrderId() {
        OrderPayInfoReadResponseDto result = bookOrderRepository.findOrderPayByOrderId("orderId")
            .orElse(null);

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(bookOrder.getOrderId(), result.getOrderId()),
            () -> assertEquals(bookOrder.getOrderName(), result.getOrderName()),
            () -> assertEquals(bookOrder.getTotalPrice(), result.getAmount())
        );
    }
}