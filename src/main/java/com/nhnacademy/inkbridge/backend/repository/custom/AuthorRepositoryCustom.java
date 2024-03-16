package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: AuthorRepositoryCustom.
 *
 * @author minm063
 * @version 2024/03/14
 */
@NoRepositoryBean
public interface AuthorRepositoryCustom {

    /**
     * 작가 아이디로 작가 정보를 조회하는 메서드입니다.
     *
     * @param authorId Long
     * @return AuthorInfoReadResponseDto
     */
    AuthorInfoReadResponseDto findByAuthorId(Long authorId);

    /**
     * 작가 전체 목록을 조회하는 메서드입니다.
     *
     * @param pageable Pageable
     * @return AuthorInfoReadResponseDto
     */
    Page<AuthorInfoReadResponseDto> findAllAuthors(Pageable pageable);
}
