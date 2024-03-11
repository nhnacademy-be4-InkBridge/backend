package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.service.CartService;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * class: CartServiceImpl.
 *
 * @author minm063
 * @version 2024/03/09
 */
@Service
public class CartServiceImpl implements CartService {

    private final BookRepository bookRepository;

    public CartServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * @return
     */
    @Override
    public List<CartReadResponseDto> getCart(Set<Long> bookIdList) {
        return bookRepository.findByBookIdIn(bookIdList);
    }
}
