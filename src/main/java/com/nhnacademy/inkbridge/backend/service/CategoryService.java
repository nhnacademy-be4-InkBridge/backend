package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;

/**
 * class: CategoryService.
 *
 * @author choijaehun
 * @version 2/15/24
 */


public interface CategoryService {

    void createCategory(CategoryCreateRequestDto request);

    void deleteCategory(Long categoryId);
}
