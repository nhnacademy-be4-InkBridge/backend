package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorCreateUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.AuthorRepository;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: AuthorServiceImplTest.
 *
 * @author minm063
 * @version 2024/03/16
 */
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @InjectMocks
    AuthorServiceImpl authorService;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    FileService fileService;

    @Mock
    Pageable pageable;

    AuthorInfoReadResponseDto authorInfoReadResponseDto;

    @BeforeEach
    void setup() {
        authorInfoReadResponseDto = mock(AuthorInfoReadResponseDto.class);
    }

    @Test
    @DisplayName("작가 번호로 작가 조회")
    void getAuthor() {
        when(authorRepository.existsById(anyLong())).thenReturn(true);
        when(authorRepository.findByAuthorId(anyLong())).thenReturn(authorInfoReadResponseDto);

        AuthorInfoReadResponseDto author = authorService.getAuthor(1L);

        assertNotNull(author);
        verify(authorRepository, times(1)).existsById(anyLong());
        verify(authorRepository, times(1)).findByAuthorId(anyLong());
    }

    @Test
    @DisplayName("작가 번호로 작가 조회 실패")
    void givenInvalidInput_whenGetAuthor_thenThrowNotFoundException() {
        when(authorRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> authorService.getAuthor(1L));
        verify(authorRepository, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("작가 이름으로 작가 조회")
    void getAuthorsByName() {
        when(authorRepository.findByAuthorName(anyString())).thenReturn(
            List.of(authorInfoReadResponseDto));

        List<AuthorInfoReadResponseDto> authorsByName = authorService.getAuthorsByName("name");

        assertEquals(1, authorsByName.size());
        verify(authorRepository, times(1)).findByAuthorName(anyString());
    }

    @Test
    @DisplayName("전체 작가 조회")
    void getAuthors() {
        Page<AuthorInfoReadResponseDto> page = new PageImpl<>(List.of(authorInfoReadResponseDto));
        when(authorRepository.findAllAuthors(any())).thenReturn(page);

        Page<AuthorInfoReadResponseDto> authors = authorService.getAuthors(pageable);

        assertEquals(1, authors.getContent().size());
        verify(authorRepository, times(1)).findAllAuthors(any(Pageable.class));
    }

    @Test
    @DisplayName("작가 등록")
    void createAuthor() {
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto = mock(
            AuthorCreateUpdateRequestDto.class);
        when(fileService.saveThumbnail(any(MultipartFile.class))).thenReturn(mock(File.class));
        when(authorRepository.save(any())).thenReturn(mock(Author.class));

        authorService.createAuthor(mock(MultipartFile.class), authorCreateUpdateRequestDto);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("작가 수정")
    void updateAuthor() {
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto = mock(
            AuthorCreateUpdateRequestDto.class);
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(mock(Author.class)));
        when(fileService.saveThumbnail(any())).thenReturn(mock(File.class));
        authorService.updateAuthor(mock(MultipartFile.class), authorCreateUpdateRequestDto, 1L);

        verify(authorRepository, times(1)).findById(anyLong());
        verify(fileService, times(1)).saveThumbnail(any(MultipartFile.class));
    }

    @Test
    @DisplayName("작가 수정 실패")
    void givenInvalidInput_whenUpdateAuthor_thenThrowException() {
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto = mock(
            AuthorCreateUpdateRequestDto.class);
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> authorService.updateAuthor(mock(MultipartFile.class),
                authorCreateUpdateRequestDto, 1L));

        verify(authorRepository, times(1)).findById(anyLong());
    }
}