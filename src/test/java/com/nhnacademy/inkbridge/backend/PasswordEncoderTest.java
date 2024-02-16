package com.nhnacademy.inkbridge.backend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * class: PasswordEncoderTest.
 *
 * @author minseo
 * @version 2/16/24
 */
@SpringBootTest
@Slf4j
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void passwordTest() {
        String password = "P@ssW0rd123";
        String encodingPassword = passwordEncoder.encode(password);

        log.info("password -> {}",password);
        log.info("encodingPassword -> {}", encodingPassword);

        assertTrue(passwordEncoder.matches(password,encodingPassword));
    }
}
