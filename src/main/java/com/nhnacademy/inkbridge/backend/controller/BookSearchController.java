package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: BookSearchController.
 *
 * @author choijaehun
 * @version 2024/03/11
 */

@RestController
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchService bookSearchService;
}
