package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
import com.nhnacademy.inkbridge.backend.service.BookStatusService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * class: BookStatusServiceImpl.
 *
 * @author minm063
 * @version 2024/02/27
 */
@Service
public class BookStatusServiceImpl implements BookStatusService {

    private final BookStatusRepository bookStatusRepository;

    public BookStatusServiceImpl(BookStatusRepository bookStatusRepository) {
        this.bookStatusRepository = bookStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookStatusReadResponseDto> getStatuses() {
        List<BookStatus> statuses = bookStatusRepository.findAllBy();
        return statuses.stream().map(
                x -> BookStatusReadResponseDto.builder().statusId(x.getStatusId())
                    .statusName(x.getStatusName()).build())
            .collect(Collectors.toList());
    }
}
