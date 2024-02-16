package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * class: CategoryService.
 *
 * @author choijaehun
 * @version 2/15/24
 */


public interface CategoryService {

    void createCategory(CategoryCreateRequestDto request);

}
