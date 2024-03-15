package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: AuthorRepositoryCustom.
 *
 * @author minm063
 * @version 2024/03/14
 */
@NoRepositoryBean
public interface AuthorRepositoryCustom {

    AuthorInfoReadResponseDto findByAuthorId(Long authorId);
}
