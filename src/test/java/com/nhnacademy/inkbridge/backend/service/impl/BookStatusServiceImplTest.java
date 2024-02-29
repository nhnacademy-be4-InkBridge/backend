package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: BookStatusServiceImplTest.
 *
 * @author minm063
 * @version 2024/02/28
 */
@ExtendWith(MockitoExtension.class)
class BookStatusServiceImplTest {

    @InjectMocks
    BookStatusServiceImpl bookStatusService;

    @Mock
    BookStatusRepository bookStatusRepository;

    @Test
    void whenGetStatuses_thenReturnDtoList() {
        BookStatus bookStatus = mock(BookStatus.class);
        when(bookStatusRepository.findAllBy()).thenReturn(List.of(bookStatus));

        List<BookStatusReadResponseDto> statuses = bookStatusService.getStatuses();

        assertEquals(1, statuses.size());
        verify(bookStatusRepository, times(1)).findAllBy();

    }
}