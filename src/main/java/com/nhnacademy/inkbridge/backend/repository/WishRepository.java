package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Wish;
import com.nhnacademy.inkbridge.backend.entity.Wish.Pk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: WishRepository.
 *
 * @author minm063
 * @version 2024/03/06
 */
public interface WishRepository extends JpaRepository<Wish, Pk> {

}
