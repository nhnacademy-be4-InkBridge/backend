package com.nhnacademy.inkbridge.backend.service.impl;

import static com.nhnacademy.inkbridge.backend.enums.BookMessageEnum.BOOK_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.CategoryMessageEnum.CATEGORY_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ALREADY_USED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_DUPLICATED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ID;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ISSUED_EXIST;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ISSUE_PERIOD_EXPIRED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_ISSUE_PERIOD_NOT_STARTED;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_TYPE_ID;
import static com.nhnacademy.inkbridge.backend.enums.CouponMessageEnum.COUPON_TYPE_NOT_FOUND;
import static com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum.MEMBER_ID;
import static com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum.MEMBER_NOT_FOUND;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.IssueCouponRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookCoupon;
import com.nhnacademy.inkbridge.backend.entity.BookCoupon.Pk;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.CategoryCoupon;
import com.nhnacademy.inkbridge.backend.entity.Coupon;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.AlreadyUsedException;
import com.nhnacademy.inkbridge.backend.exception.InvalidPeriodException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.CouponRepository;
import com.nhnacademy.inkbridge.backend.repository.CouponTypeRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: CouponServiceImpl.
 *
 * @author JBum
 * @version 2024/02/15
 */
@Service
public class CouponServiceImpl implements CouponService {

    private static final int COUPON_LENGTH = 10;
    private final CouponRepository couponRepository;
    private final CouponTypeRepository couponTypeRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryCouponRepository categoryCouponRepository;
    private final BookRepository bookRepository;
    private final BookCouponRepository bookCouponRepository;

    /**
     * couponService에 필요한 Repository들 주입.
     *
     * @param couponRepository         쿠폰
     * @param couponTypeRepository     쿠폰타입
     * @param memberRepository         멤버
     * @param memberCouponRepository   멤버가 가진 쿠폰
     * @param categoryRepository       카테고리
     * @param categoryCouponRepository 카테고리전용 쿠폰
     * @param bookRepository           책
     * @param bookCouponRepository     책전용 쿠폰
     */
    public CouponServiceImpl(CouponRepository couponRepository,
        CouponTypeRepository couponTypeRepository, MemberRepository memberRepository,
        MemberCouponRepository memberCouponRepository, CategoryRepository categoryRepository,
        CategoryCouponRepository categoryCouponRepository, BookRepository bookRepository,
        BookCouponRepository bookCouponRepository) {
        this.couponRepository = couponRepository;
        this.couponTypeRepository = couponTypeRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
        this.categoryRepository = categoryRepository;
        this.categoryCouponRepository = categoryCouponRepository;
        this.bookRepository = bookRepository;
        this.bookCouponRepository = bookCouponRepository;
    }

    /**
     * 관리자가 쿠폰을 생성하는 메소드. 관리자는 쿠폰을 등록하면서 해당 쿠폰을 특정한 책 전용 혹은 특정한 카테고리 전용으로 만들 수 있다.
     *
     * @param couponCreateRequestDto 쿠폰을 생성하기 위한 Request DTO
     * @throws NotFoundException    입력된 쿠폰 타입이 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 카테고리가 존재하지 않는 경우 예외 발생
     * @throws NotFoundException    요청한 책이 존재하지 않는 경우 예외 발생
     * @throws AlreadyUsedException 존재하지 않는 쿠폰타입이면 예외 발생
     */
    @Override
    @Transactional
    public void createCoupon(CouponCreateRequestDto couponCreateRequestDto) {
        CouponType couponType =
            couponTypeRepository.findById(couponCreateRequestDto.getCouponTypeId())
                .orElseThrow(() -> new NotFoundException(
                    String.format("%s%s%d", COUPON_TYPE_NOT_FOUND.getMessage(),
                        COUPON_TYPE_ID.getMessage(), couponCreateRequestDto.getCouponTypeId())));
        if (couponRepository.existsByCouponName(couponCreateRequestDto.getCouponName())) {
            throw new AlreadyUsedException(COUPON_DUPLICATED.getMessage());
        }
        Coupon newCoupon = Coupon.builder()
            .couponId(generateCoupon())
            .couponType(couponType)
            .couponName(couponCreateRequestDto.getCouponName())
            .basicExpiredDate(couponCreateRequestDto.getBasicExpiredDate())
            .basicIssuedDate(couponCreateRequestDto.getBasicIssuedDate())
            .discountPrice(couponCreateRequestDto.getDiscountPrice())
            .maxDiscountPrice(couponCreateRequestDto.getMaxDiscountPrice())
            .minPrice(couponCreateRequestDto.getMinPrice())
            .isBirth(couponCreateRequestDto.getIsBirth())
            .validity(couponCreateRequestDto.getValidity())
            .build();
        couponRepository.saveAndFlush(newCoupon);
        List<Long> categoryIds = couponCreateRequestDto.getCategoryIds();
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                    CATEGORY_NOT_FOUND.getMessage()));
            saveCategoryCoupon(category, newCoupon);
        }

        List<Long> bookIds = couponCreateRequestDto.getBookIds();
        for (Long bookId : bookIds) {
            Book book = bookRepository.findById(bookId)
                .orElseThrow(
                    () -> new NotFoundException(BOOK_NOT_FOUND.getMessage()));
            saveBookCoupon(book, newCoupon);
        }
    }

    /**
     * 사용자가 쿠폰을 등록하는 메소드.
     *
     * @param issueCouponDto 쿠폰을 등록하기 위한 Request DTO
     * @throws NotFoundException      존재하지 않는 쿠폰이 입력된 경우 예외 발생
     * @throws AlreadyExistException  이미 등록된 쿠폰인 경우 예외 발생
     * @throws InvalidPeriodException 쿠폰 발급이 가능한 날짜가 아닌 경우 예외 발생
     */
    @Override
    public void issueCoupon(IssueCouponRequestDto issueCouponDto) {
        Coupon coupon = couponRepository.findById(issueCouponDto.getCouponId())
            .orElseThrow(() -> new NotFoundException(
                String.format("%s%s%d", COUPON_NOT_FOUND.getMessage(), COUPON_ID.getMessage(),
                    issueCouponDto.getCouponId())));
        Member member = memberRepository.findById(issueCouponDto.getMemberId())
            .orElseThrow(() -> new NotFoundException(
                String.format("%s%s%d", MEMBER_NOT_FOUND.getMessage(), MEMBER_ID.getMessage(),
                    issueCouponDto.getMemberId())));
        validateCouponPeriod(coupon.getBasicIssuedDate(), coupon.getBasicExpiredDate());
        if (memberCouponRepository.existsByCouponAndMember(coupon, member)) {
            throw new AlreadyExistException(COUPON_ISSUED_EXIST.getMessage());
        }
        MemberCoupon memberCoupon = MemberCoupon.builder()
            .memberCouponId(generateCoupon())
            .member(member)
            .coupon(coupon)
            .issuedAt(LocalDate.now())
            .expiredAt(LocalDate.now().plusDays(coupon.getValidity()))
            .build();
        memberCouponRepository.saveAndFlush(memberCoupon);
    }

    /**
     * 쿠폰ID를 생성하는 메소드. 쿠폰ID는 예측이 불가능해야 하므로 SecureRandom을 사용하여 안전한 난수를 생성한다.
     *
     * @return 랜덤한 BigInteger값을 16진수로 변경한 쿠폰ID
     */
    private String generateCoupon() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[COUPON_LENGTH / 2];
        secureRandom.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(16);
    }

    /**
     * Member가 사용했는 쿠폰인지 확인하는 메소드. d t ad
     *
     * @param memberCoupon 멤버의 쿠폰
     * @throws AlreadyUsedException 사용한 쿠폰이라면 예외 발생
     */
    private void validateCouponUsed(MemberCoupon memberCoupon) {
        if (memberCoupon.getUsedAt() == null) {
            throw new AlreadyUsedException(COUPON_ALREADY_USED.getMessage());
        }
    }

    /**
     * 발급 가능한 날짜 인지 체크 하는 메소드.
     *
     * @param startDate 발급 시작 날짜
     * @param endDate   발급 종료 날짜
     * @throws InvalidPeriodException 발급 기한이 아닌 경우 예외 발생
     */
    private void validateCouponPeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(startDate)) {
            throw new InvalidPeriodException(COUPON_ISSUE_PERIOD_NOT_STARTED.getMessage());
        } else if (now.isAfter(endDate)) {
            throw new InvalidPeriodException(COUPON_ISSUE_PERIOD_EXPIRED.getMessage());
        }
    }

    /**
     * 카테고리 쿠폰을 저장하는 메소드.
     *
     * @param category 적용할 카테고리
     * @param coupon   적용할 쿠폰
     */
    private void saveCategoryCoupon(Category category, Coupon coupon) {
        CategoryCoupon categoryCoupon = CategoryCoupon.builder()
            .category(category)
            .coupon(coupon)
            .pk(
                CategoryCoupon.Pk.builder()
                    .couponId(coupon.getCouponId())
                    .categoryId(category.getCategoryId())
                    .build())
            .build();
        categoryCouponRepository.save(categoryCoupon);
    }

    /**
     * 책 쿠폰을 저장하는 메소드.
     *
     * @param book   적용할 책
     * @param coupon 적용할 쿠폰
     */
    private void saveBookCoupon(Book book, Coupon coupon) {
        BookCoupon bookCoupon = BookCoupon.builder()
            .coupon(coupon)
            .book(book)
            .pk(Pk.builder()
                .bookId(book.getBookId())
                .couponId(coupon.getCouponId())
                .build())
            .build();
        bookCouponRepository.save(bookCoupon);
    }
}