package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.book.AuthorRequestDto;
import com.nhnacademy.inkbridge.backend.repository.AuthorRepository;
import com.nhnacademy.inkbridge.backend.service.AuthorService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * class: AuthorServiceImpl.
 *
 * @author JBum
 * @version 2024/02/29
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorRequestDto getAuthor(Long AuthorId) {
        return null;
    }

    @Override
    public List<AuthorRequestDto> getAuthorList() {
        return null;
    }
}
