package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: CategoryRepository.
 *
 * @author JBum
 * @version 2024/02/19
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
