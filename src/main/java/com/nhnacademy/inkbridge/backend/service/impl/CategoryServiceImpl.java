package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.enums.CategoryMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
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

    @Override
    public CategoryUpdateResponseDto updateCategory(Long categoryId, CategoryUpdateRequestDto request) {
        Category currentCategory = categoryRepository.findById(categoryId).orElseThrow(
            () -> new NotFoundException(CategoryMessageEnum.CATEGORY_NOT_FOUND.toString()));
        currentCategory.updateCategory(request.getCategoryName());

        return CategoryUpdateResponseDto.builder().categoryName(request.getCategoryName()).build();
    }
    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        Category currentCategory = categoryRepository.findById(categoryId).orElse(null);
        if (currentCategory == null) {
            throw new NotFoundException(CategoryMessageEnum.CATEGORY_NOT_FOUND.toString());
        }
        if (currentCategory.getCategoryChildren() == null) {
            throw new ValidationException(CategoryMessageEnum.SUB_CATEGORY_EXIST.toString());
        }

        categoryRepository.delete(currentCategory);
    }
}
