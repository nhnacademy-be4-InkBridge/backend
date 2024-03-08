package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoriesDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: TagCustomRepository.
 *
 * @author JBum
 * @version 2024/03/08
 */

@NoRepositoryBean
public interface MemberCouponCustomRepository {

    List<MemberCouponReadResponseDto> findOrderCoupons(Long memberId,
        BookCategoriesDto bookCategoriesDto);
}
