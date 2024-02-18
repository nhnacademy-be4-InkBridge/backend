package com.nhnacademy.inkbridge.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: EurekaTestController.
 *
 * @author jangjaehun
 * @version 2024/02/18
 */
@RestController
@RequestMapping("/api/test")
public class EurekaTestController {

    @Value("${spring.application.name}")
    private String name;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return name;
    }
}
