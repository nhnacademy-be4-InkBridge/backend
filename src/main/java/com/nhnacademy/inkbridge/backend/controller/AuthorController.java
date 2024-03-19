package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorCreateUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.AuthorService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: AuthorController.
 *
 * @author minm063
 * @version 2024/03/14
 */
@Slf4j
@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * 작가 아이디에 대해 작가 정보를 조회하는 api입니다.
     *
     * @param authorId Long
     * @param pageable Pageable
     * @return AuthorReadResponseDto
     */
    @GetMapping("/api/authors/{authorId}")
    public ResponseEntity<AuthorInfoReadResponseDto> getAuthor(@PathVariable Long authorId,
        Pageable pageable) {
        AuthorInfoReadResponseDto author = authorService.getAuthor(authorId);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /**
     * 작가 이름으로 작가 정보를 조회하는 api입니다.
     *
     * @param authorName String
     * @return AuthorInfoReadResponseDto
     */
    @GetMapping("/api/authors")
    public ResponseEntity<List<AuthorInfoReadResponseDto>> getAuthorByName(
        @RequestParam(name = "authorName") String authorName) {
        List<AuthorInfoReadResponseDto> author = authorService.getAuthorByName(authorName);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /**
     * 관리자 페이지에서 전체 작가를 조회하는 api입니다.
     *
     * @param pageable Pageable
     * @return AuthorInfoReadResponseDto
     */
    @GetMapping("/api/admin/authors")
    public ResponseEntity<Page<AuthorInfoReadResponseDto>> getAuthors(Pageable pageable) {
        Page<AuthorInfoReadResponseDto> authors = authorService.getAuthors(pageable);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    /**
     * 관리자 페이지에서 작가 정보를 등록하는 api입니다.
     *
     * @param authorFile                   MultipartFile
     * @param authorCreateUpdateRequestDto AuthorCreateUpdateRequestDto
     * @return HttpStatus
     */
    @PostMapping("/api/admin/authors")
    public ResponseEntity<HttpStatus> createAuthor(
        @RequestPart("image") MultipartFile authorFile, @Valid @RequestPart("author")
    AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        authorService.createAuthor(authorFile, authorCreateUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 관리자 페이지에서 작가 정보를 수정하는 api입니다.
     *
     * @param authorId                     Long
     * @param authorFile                   MultipartFile
     * @param authorCreateUpdateRequestDto AuthorCreateUpdateRequestDto
     * @return HttpStatus
     */
    @PutMapping("/api/admin/authors/{authorId}")
    public ResponseEntity<HttpStatus> updateAuthor(@PathVariable Long authorId,
        @RequestPart(value = "image", required = false) MultipartFile authorFile,
        @Valid @RequestPart("author") AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        authorService.updateAuthor(authorFile, authorCreateUpdateRequestDto, authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
