package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorReadResponseDto;
import org.springframework.data.domain.Pageable;

/**
 * class: AuthorService.
 *
 * @author minm063
 * @version 2024/03/14
 */
public interface AuthorService {

    AuthorReadResponseDto getAuthor(Long authorId, Pageable pageable);
}
