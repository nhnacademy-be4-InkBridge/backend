package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingResponseDto;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.WrappingRepository;
import com.nhnacademy.inkbridge.backend.service.WrappingService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * class: WrappingServiceImpl.
 *
 * @author JBum
 * @version 2024/02/28
 */
@Service
public class WrappingServiceImpl implements WrappingService {

    private final WrappingRepository wrappingRepository;

    /**
     * wrappingService 생성자.
     *
     * @param wrappingRepository WrappingService에 주입해줄 WrappingRepository
     */
    public WrappingServiceImpl(WrappingRepository wrappingRepository) {
        this.wrappingRepository = wrappingRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<WrappingResponseDto> getWrappingList() {
        return wrappingRepository.findAllBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WrappingResponseDto getWrapping(Long wrappingId) {
        return wrappingRepository.findByWrappingId(wrappingId)
            .orElseThrow(() -> new NotFoundException(
                OrderMessageEnum.Wrapping_NOT_FOUND.getMessage()));
    }
}
