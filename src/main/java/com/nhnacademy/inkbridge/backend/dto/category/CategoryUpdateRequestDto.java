package com.nhnacademy.inkbridge.backend.dto.category;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: CategoryUpdateRequestDto.
 *
 * @author choijaehun
 * @version 2/16/24
 */

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryUpdateRequestDto {
    @Size(min=1,max=10)
    private String categoryName;
}
