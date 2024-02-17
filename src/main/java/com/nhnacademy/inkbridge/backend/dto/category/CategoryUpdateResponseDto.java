package com.nhnacademy.inkbridge.backend.dto.category;

import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CategoryUpdateResponseDto.
 *
 * @author choijaehun
 * @version 2/16/24
 */
@Getter
@NoArgsConstructor
public class CategoryUpdateResponseDto {

    @Size(message = "카테고리 명은 10글자 이하로 작성해야합니다.", min = 1, max = 10)
    private String categoryName;

    @Builder
    public CategoryUpdateResponseDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
