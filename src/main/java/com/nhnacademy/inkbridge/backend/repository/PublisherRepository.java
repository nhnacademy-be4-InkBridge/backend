package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.book.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: PublisherRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    List<PublisherReadResponseDto> findAllBy();
}
