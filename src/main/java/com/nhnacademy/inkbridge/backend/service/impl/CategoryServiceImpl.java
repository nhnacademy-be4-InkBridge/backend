package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import com.nhnacademy.inkbridge.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: CategoryServiceImpl.
 *
 * @author choijaehun
 * @version 2/15/24
 */

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createCategory(CategoryCreateRequestDto request) {
        Category parentCategory = categoryRepository.findById(request.getParentId()).orElse(null);
        Category newCategory = Category.create()
            .categoryName(request.getCategoryName())
            .categoryParent(parentCategory)
            .build();
        categoryRepository.save(newCategory);
    }


}
