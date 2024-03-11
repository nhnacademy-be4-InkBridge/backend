package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import java.util.List;
import java.util.Set;

/**
 * class: CartService.
 *
 * @author minm063
 * @version 2024/03/09
 */
public interface CartService {

    List<CartReadResponseDto> getCart(Set<Long> bookIdList);
}
