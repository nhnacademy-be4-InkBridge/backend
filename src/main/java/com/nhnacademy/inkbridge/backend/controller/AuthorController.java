package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.AuthorRequestDto;
import com.nhnacademy.inkbridge.backend.service.AuthorService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AuthorController.
 *
 * @author JBum
 * @version 2024/02/29
 */
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorRequestDto>> getAuthors() {
        return ResponseEntity.ok(authorService.getAuthorList());
    }

    @GetMapping("/{author_id}")
    public ResponseEntity<AuthorRequestDto> getAuthor(@PathVariable("author_id") Long authorId) {
        return ResponseEntity.ok(authorService.getAuthor(authorId));

    }
}
