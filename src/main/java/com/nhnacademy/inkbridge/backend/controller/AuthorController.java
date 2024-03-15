package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.AuthorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AuthorController.
 *
 * @author minm063
 * @version 2024/03/14
 */
@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/authors/{authorId}")
    public ResponseEntity<AuthorReadResponseDto> getAuthor(@PathVariable Long authorId,
        Pageable pageable) {
        AuthorReadResponseDto authorReadResponseDto = authorService.getAuthor(authorId, pageable);
        return new ResponseEntity<>(authorReadResponseDto, HttpStatus.OK);
    }
}
