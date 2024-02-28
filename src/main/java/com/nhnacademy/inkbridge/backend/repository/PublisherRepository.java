package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.PublisherResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: PublisherRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * 특정 publisher에 정보를 Optional형태로 가져올수 있음
     *
     * @param publisherId 정보가 필요한 publisherId
     * @return publisher정보
     */
    Optional<PublisherResponseDto> findByPublisherId(Long publisherId);

    /**
     * 전체 publisher의 정보를 볼 수 있는 메소드
     *
     * @return 전체 publisher
     */
    List<PublisherResponseDto> findAllBy();
}
