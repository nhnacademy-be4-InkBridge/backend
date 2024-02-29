package com.nhnacademy.inkbridge.backend.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: BookCouponRepositoryTest.
 *
 * @author JBum
 * @version 2024/02/20
 */
@DataJpaTest
@ActiveProfiles("local")
@SpringBootTest
@Transactional
class BookCouponRepositoryTest {

}