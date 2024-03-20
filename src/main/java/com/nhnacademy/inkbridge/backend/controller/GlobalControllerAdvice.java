package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.ApiError;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * class: ControllerAdvice.
 *
 * @author minm063
 * @version 2024/02/15
 */
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<ApiError> handleAlreadyExistException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiError> handleValidationException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ApiError(e.getBindingResult().getAllErrors().toString()), HttpStatus.BAD_REQUEST);
    }
}
