package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewBookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewMemberReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.FileService;
import com.nhnacademy.inkbridge.backend.service.ReviewService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: ReviewController.
 *
 * @author minm063
 * @version 2024/03/19
 */
@RequestMapping("/api/reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final FileService fileService;

    public ReviewController(ReviewService reviewService, FileService fileService) {
        this.reviewService = reviewService;
        this.fileService = fileService;
    }

    /**
     * 회원 번호로 리뷰를 조회하는 api입니다.
     *
     * @param memberId Long
     * @param pageable Pageable
     * @return ReviewReadResponseDto
     */
    @GetMapping
    public ResponseEntity<ReviewMemberReadResponseDto> getReviewsByMember(
        @RequestParam(name = "memberId") Long memberId, Pageable pageable) {
        ReviewMemberReadResponseDto reviews = reviewService.getReviewsByMember(pageable, memberId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * 도서 번호로 리뷰를 조회하는 api입니다.
     *
     * @param bookId Long
     * @param pageable Long
     * @return ReviewReadResponseDto
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<ReviewBookReadResponseDto> getReviewsByBookId(@PathVariable Long bookId,
        @PageableDefault(size = 5) Pageable pageable) {
        ReviewBookReadResponseDto reviews = reviewService.getReviewsByBookId(pageable, bookId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * 리뷰를 등록하는 api입니다.
     *
     * @param reviewImages MultipartFile List
     * @param memberId Long
     * @param reviewCreateRequestDto ReviewCreateRequestDto
     * @param bindingResult BindingResult
     * @return ResponseEntity HttpStatus
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createReview(
        @RequestPart(name = "images", required = false) List<MultipartFile> reviewImages,
        @RequestParam(name = "memberId") Long memberId,
        @Valid @RequestPart(name = "review") ReviewCreateRequestDto reviewCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        List<File> files =
            Objects.isNull(reviewImages) ? Collections.emptyList() : reviewImages.stream()
                .map(fileService::saveThumbnail).collect(
                    Collectors.toList());

        reviewService.createReview(memberId, reviewCreateRequestDto, files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 리뷰를 수정하는 api입니다.
     *
     * @param reviewId Long
     * @param memberId Long
     * @param reviewImages MultipartFile List
     * @param reviewCreateRequestDto ReviewCreateRequestDto
     * @param bindingResult BindingResult
     * @return HttpStatus
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<HttpStatus> updateReview(@PathVariable(name = "reviewId") Long reviewId,
        @RequestParam(name = "memberId") Long memberId,
        @RequestPart(name = "reviewImages", required = false) List<MultipartFile> reviewImages,
        @Valid @RequestPart(name = "review") ReviewCreateRequestDto reviewCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        List<File> files =
            Objects.isNull(reviewImages) ? Collections.emptyList() : reviewImages.stream()
                .map(fileService::saveThumbnail).collect(
                    Collectors.toList());

        reviewService.updateReview(memberId, reviewId, reviewCreateRequestDto, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
