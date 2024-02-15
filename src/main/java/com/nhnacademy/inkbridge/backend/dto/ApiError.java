package com.nhnacademy.inkbridge.backend.dto;

import lombok.Builder;

/**
 * class: ApiError.
 *
 * @author minm063
 * @version 2024/02/15
 */
public class ApiError {
    private final String message;

    @Builder
    public ApiError(String message) {
        this.message = message;
    }
}
