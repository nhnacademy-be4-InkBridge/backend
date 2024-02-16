package com.nhnacademy.inkbridge.backend.dto.category;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: CategoryCreateRequestDto.
 *
 * @author choijaehun
 * @version 2/16/24
 */

@NoArgsConstructor
@Getter
@Setter
public class CategoryCreateRequestDto {
    @Size(message = "카테고리 명은 10글자 이하로 작성해야합니다.", min = 1, max = 10)
    private String categoryName;
    private Long parentId;
}
