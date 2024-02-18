package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.repository.custom.TagCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: TagRepository.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */

public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {

    Boolean existsByTagName(String tagName);
}

