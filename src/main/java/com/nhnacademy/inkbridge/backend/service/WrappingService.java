package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingResponseDto;
import java.util.List;

/**
 * class: WrappingService.
 *
 * @author JBum
 * @version 2024/02/28
 */
public interface WrappingService {

    /**
     * 모든 wrappingList 가져오기.
     *
     * @returnr WrappingList
     */
    List<WrappingResponseDto> getWrappingList();

    /**
     * wrappingId의 wrapping정보 가져오기.
     *
     * @param wrappingId 조회할 wrappingId
     * @return 조회한 wrapping
     */
    WrappingResponseDto getWrapping(Long wrappingId);
}
