package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorCreateUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: AuthorService.
 *
 * @author minm063
 * @version 2024/03/14
 */
public interface AuthorService {

    /**
     * 도서 정보를 포함한 작가 상세 정보를 조회하는 메서드입니다.
     *
     * @param authorId Long
     * @param pageable Pageable
     * @return AuthorReadResponseDto
     */
    AuthorReadResponseDto getAuthor(Long authorId, Pageable pageable);

    /**
     * 작가 전체 목록을 조회하는 메서드입니다.
     *
     * @param pageable Pageable
     * @return AuthorInfoReadResponseDto
     */
    Page<AuthorInfoReadResponseDto> getAuthors(Pageable pageable);

    /**
     * 작가 정보를 등록하는 메서드입니다.
     *
     * @param authorFile                   MultipartFile
     * @param authorCreateUpdateRequestDto AuthorCreateUpdateRequestDto
     */
    void createAuthor(MultipartFile authorFile,
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto);

    /**
     * 작가 정보를 수정하는 메서드입니다.
     *
     * @param authorFile                   MultipartFile
     * @param authorCreateUpdateRequestDto AuthorCreateUpdateRequestDto
     * @param authorId                     Long
     */
    void updateAuthor(MultipartFile authorFile,
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto,
        Long authorId);
}
