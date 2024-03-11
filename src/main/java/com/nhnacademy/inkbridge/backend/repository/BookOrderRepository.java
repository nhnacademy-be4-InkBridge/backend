package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookOrderRepository.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
public interface BookOrderRepository extends JpaRepository<BookOrder, String> {

}
