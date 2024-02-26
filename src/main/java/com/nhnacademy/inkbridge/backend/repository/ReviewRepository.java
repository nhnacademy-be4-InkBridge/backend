package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: ReviewRepository.
 *
 * @author jeongbyeonghun
 * @version 2/26/24
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
