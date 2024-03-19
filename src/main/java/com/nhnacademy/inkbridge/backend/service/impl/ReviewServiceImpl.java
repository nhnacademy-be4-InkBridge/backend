package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.review.ReviewReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookOrderDetail;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.entity.ReviewFile;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.BookOrderDetailMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.ReviewMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookOrderDetailRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewFileRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewRepository;
import com.nhnacademy.inkbridge.backend.service.ReviewService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: ReviewServiceImpl.
 *
 * @author minm063
 * @version 2024/03/19
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookOrderDetailRepository bookOrderDetailRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, MemberRepository memberRepository,
        ReviewFileRepository reviewFileRepository, BookRepository bookRepository,
        BookOrderDetailRepository bookOrderDetailRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.reviewFileRepository = reviewFileRepository;
        this.bookRepository = bookRepository;
        this.bookOrderDetailRepository = bookOrderDetailRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReviewReadResponseDto> getReviews(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }

        return reviewRepository.findByMemberId(memberId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createReview(ReviewCreateRequestDto reviewCreateRequestDto, List<File> files) {
        Member member = memberRepository.findById(reviewCreateRequestDto.getMemberId())
            .orElseThrow(
                () -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));
        Book book = bookRepository.findById(reviewCreateRequestDto.getBookId())
            .orElseThrow(() -> new NotFoundException(
                BookMessageEnum.BOOK_NOT_FOUND.getMessage()));
        BookOrderDetail bookOrderDetail = bookOrderDetailRepository.findById(
                reviewCreateRequestDto.getOrderDetailId())
            .orElseThrow(() -> new NotFoundException(
                BookOrderDetailMessageEnum.BOOK_ORDER_DETAIL_NOT_FOUND.getMessage()));

        Review review = Review.builder().member(member).book(book).bookOrderDetail(bookOrderDetail)
            .reviewTitle(reviewCreateRequestDto.getReviewTitle()).reviewContent(
                reviewCreateRequestDto.getReviewContent()).registeredAt(LocalDateTime.now())
            .score(reviewCreateRequestDto.getScore()).build();
        reviewRepository.save(review);

        List<ReviewFile> reviewFiles = files.stream().map(
                file -> ReviewFile.builder().fileId(file.getFileId()).file(file).review(review).build())
            .collect(
                Collectors.toList());
        reviewFileRepository.saveAll(reviewFiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateReview(Long reviewId, ReviewCreateRequestDto reviewCreateRequestDto,
        List<File> files) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException(
            ReviewMessageEnum.REVIEW_NOT_FOUND.getMessage()));

        if (!memberRepository.existsById(reviewCreateRequestDto.getMemberId())) {
            throw new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        }
        if (!bookRepository.existsById(reviewCreateRequestDto.getBookId())) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage());
        }
        if (!bookOrderDetailRepository.existsById(reviewCreateRequestDto.getOrderDetailId())) {
            throw new NotFoundException(
                BookOrderDetailMessageEnum.BOOK_ORDER_DETAIL_NOT_FOUND.getMessage());
        }

        review.updateReview(reviewCreateRequestDto.getReviewTitle(),
            reviewCreateRequestDto.getReviewContent(), LocalDateTime.now(),
            reviewCreateRequestDto.getScore());

        reviewFileRepository.deleteByFile_FileIdInAndReview_ReviewId(
            files.stream().map(File::getFileId).collect(Collectors.toList()), reviewId);

        List<ReviewFile> reviewFiles = files.stream().map(
                file -> ReviewFile.builder().fileId(file.getFileId()).file(file).review(review).build())
            .collect(Collectors.toList());
        reviewFileRepository.saveAll(reviewFiles);
    }
}
