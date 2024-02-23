package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: TagRepository.
 *
 * @author minm063
 * @version 2024/02/21
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

}
