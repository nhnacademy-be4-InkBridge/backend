package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.book.AuthorRequestDto;
import java.util.List;

/**
 * class: AuthorService.
 *
 * @author JBum
 * @version 2024/02/29
 */
public interface AuthorService {

    AuthorRequestDto getAuthor(Long AuthorId);

    List<AuthorRequestDto> getAuthorList();
}
