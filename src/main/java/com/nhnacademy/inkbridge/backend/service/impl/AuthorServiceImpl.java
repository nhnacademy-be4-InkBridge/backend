package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorCreateUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.enums.AuthorMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.AuthorRepository;
import com.nhnacademy.inkbridge.backend.service.AuthorService;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: AuthorServiceImpl.
 *
 * @author minm063
 * @version 2024/03/14
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileService fileService;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileService fileService) {
        this.authorRepository = authorRepository;
        this.fileService = fileService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public AuthorInfoReadResponseDto getAuthor(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new NotFoundException(AuthorMessageEnum.AUTHOR_NOT_FOUND.getMessage());
        }

        return authorRepository.findByAuthorId(authorId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<AuthorInfoReadResponseDto> getAuthorsByName(String authorName) {
        return authorRepository.findByAuthorName(authorName);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Page<AuthorInfoReadResponseDto> getAuthors(Pageable pageable) {
        return authorRepository.findAllAuthors(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createAuthor(MultipartFile authorFile,
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto) {
        File file = fileService.saveThumbnail(authorFile);
        Author author = Author.builder().authorName(authorCreateUpdateRequestDto.getAuthorName())
            .authorIntroduce(
                authorCreateUpdateRequestDto.getAuthorIntroduce()).file(file).build();
        authorRepository.save(author);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateAuthor(MultipartFile authorFile,
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto,
        Long authorId) {
        Author author = authorRepository.findById(authorId)
            .orElseThrow(
                () -> new NotFoundException(AuthorMessageEnum.AUTHOR_NOT_FOUND.getMessage()));
        File file = (authorFile == null) ? author.getFile() : fileService.saveThumbnail(authorFile);
        author.updateAuthor(authorCreateUpdateRequestDto.getAuthorName(),
            authorCreateUpdateRequestDto.getAuthorIntroduce(), file);
    }
}
