package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import java.util.List;

/**
 * class: BookStatusService.
 *
 * @author minm063
 * @version 2024/02/27
 */
public interface BookStatusService {

    List<BookStatusReadResponseDto> getStatuses();
}
