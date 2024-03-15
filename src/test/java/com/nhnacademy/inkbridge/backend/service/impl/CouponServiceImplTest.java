//package com.nhnacademy.inkbridge.backend.service.impl;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.nhnacademy.inkbridge.backend.dto.coupon.BookCouponCreateRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.coupon.CategoryCouponCreateRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.coupon.CouponCreateRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.coupon.CouponIssueRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
//import com.nhnacademy.inkbridge.backend.entity.Book;
//import com.nhnacademy.inkbridge.backend.entity.Category;
//import com.nhnacademy.inkbridge.backend.entity.Coupon;
//import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
//import com.nhnacademy.inkbridge.backend.entity.CouponType;
//import com.nhnacademy.inkbridge.backend.entity.Member;
//import com.nhnacademy.inkbridge.backend.entity.MemberCoupon;
//import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
//import com.nhnacademy.inkbridge.backend.repository.BookCouponRepository;
//import com.nhnacademy.inkbridge.backend.repository.BookRepository;
//import com.nhnacademy.inkbridge.backend.repository.CategoryCouponRepository;
//import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
//import com.nhnacademy.inkbridge.backend.repository.CouponRepository;
//import com.nhnacademy.inkbridge.backend.repository.CouponStatusRepository;
//import com.nhnacademy.inkbridge.backend.repository.CouponTypeRepository;
//import com.nhnacademy.inkbridge.backend.repository.MemberCouponRepository;
//import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
///**
// * class: CouponServiceImplTest.
// *
// * @author JBum
// * @version 2024/02/19
// */
//
//@ExtendWith(MockitoExtension.class)
//class CouponServiceImplTest {
//
//    @InjectMocks
//    private CouponServiceImpl couponService;
//
//    @Mock
//    private CouponRepository couponRepository;
//    @Mock
//    private CouponTypeRepository couponTypeRepository;
//    @Mock
//    private CouponStatusRepository couponStatusRepository;
//    @Mock
//    private MemberRepository memberRepository;
//    @Mock
//    private MemberCouponRepository memberCouponRepository;
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @Mock
//    private CategoryCouponRepository categoryCouponRepository;
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private BookCouponRepository bookCouponRepository;
//
//    @Test
//    @DisplayName("정상적인쿠폰 생성")
//    void testCreateCoupon_Coupon_Create_Success() {
//        // Given
//        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
//            .couponTypeId(1)
//            .couponName("테스트 쿠폰")
//            .basicIssuedDate(LocalDate.now())
//            .basicExpiredDate(LocalDate.now().plusDays(30))
//            .discountPrice(1000L)
//            .isBirth(false)
//            .maxDiscountPrice(5000L)
//            .minPrice(10000L)
//            .validity(7)
//            .build();
//
//        CouponType couponType = CouponType.builder()
//            .couponTypeId(1)
//            .build();
//        when(couponTypeRepository.findById(1)).thenReturn(Optional.of(couponType));
//
//        CouponStatus couponStatus = CouponStatus.builder()
//            .couponStatusId(1)
//            .build();
//        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));
//
//        // When
//        couponService.createCoupon(couponCreateRequestDto);
//
//        // Then
//        verify(couponRepository, times(1)).saveAndFlush(any());
//    }
//
//
//    @Test
//    @DisplayName("쿠폰 생성 실패: 유효하지 않은 유효기간")
//    void testCreateCoupon_Fail_InvalidPeriod() {
//        // Given
//        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
//            .couponTypeId(1)
//            .basicIssuedDate(LocalDate.now().minusDays(1)) // 현재 날짜보다 이전인 유효 기간
//            .build();
//        when(couponTypeRepository.findById(1)).thenReturn(
//            Optional.of(CouponType.builder().build()));
//
//        // When & Then
//        assertThrows(NotFoundException.class,
//            () -> couponService.createCoupon(couponCreateRequestDto));
//        verify(couponRepository, never()).saveAndFlush(any());
//    }
//
//    @DisplayName("쿠폰 생성 실패: 유효하지 않은 쿠폰 타입")
//    @Test
//    void testCreateCoupon_Fail_InvalidCouponType() {
//        // Given
//        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
//            .couponTypeId(1)
//            .build();
//        when(couponTypeRepository.findById(1)).thenReturn(Optional.empty());
//
//        // When & Then
//        assertThrows(NotFoundException.class,
//            () -> couponService.createCoupon(couponCreateRequestDto));
//        verify(couponRepository, never()).saveAndFlush(any());
//    }
//
//    @Test
//    @DisplayName("쿠폰 생성 실패: 유효하지 않은 쿠폰 상태")
//    void testFindCouponStatus_Fail() {
//        // Given
//        CouponType couponType = mock(CouponType.class);
//
//        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
//            .couponTypeId(6)
//            .couponName("테스트 쿠폰")
//            .basicIssuedDate(LocalDate.now())
//            .basicExpiredDate(LocalDate.now())
//            .discountPrice(1000L)
//            .isBirth(false)
//            .maxDiscountPrice(5000L)
//            .minPrice(10000L)
//            .validity(7)
//            .build();
//
//        // When
//        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
//
//        assertThrows(NotFoundException.class, () -> {
//            couponService.createCoupon(couponCreateRequestDto);
//        });
//        // Then
//        verify(couponRepository, never()).saveAndFlush(any());
//    }
//
//    @Test
//    @DisplayName("쿠폰 생성 실패: 쿠폰 상태(어제)")
//    void testFindCouponStatusByIssuedDate_BeforeToday() {
//        // Given
//        CouponType couponType = mock(CouponType.class);
//
//        CouponCreateRequestDto couponCreateRequestDto = CouponCreateRequestDto.builder()
//            .couponTypeId(1)
//            .couponName("테스트 쿠폰")
//            .basicIssuedDate(LocalDate.now().minusDays(1))
//            .basicExpiredDate(LocalDate.now().minusDays(30))
//            .discountPrice(1000L)
//            .isBirth(false)
//            .maxDiscountPrice(5000L)
//            .minPrice(10000L)
//            .validity(7)
//            .build();
//
//        //when
//        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
//
//        // Then
//        assertThrows(NotFoundException.class, () -> {
//            couponService.createCoupon(couponCreateRequestDto);
//        });
//        verify(couponRepository, never()).saveAndFlush(any());
//
//    }
//
//    @Test
//    @DisplayName("카테고리 쿠폰 생성 성공")
//    void createCategoryCoupon() {
//        // Given
//        CategoryCouponCreateRequestDto categoryCouponCreateRequestDto = CategoryCouponCreateRequestDto.builder()
//            .couponName("Test Coupon")
//            .couponTypeId(1)
//            .basicIssuedDate(LocalDate.now())
//            .basicExpiredDate(LocalDate.now().plusDays(7))
//            .discountPrice(10L)
//            .maxDiscountPrice(100L)
//            .minPrice(50L)
//            .validity(30)
//            .categoryIds(new HashSet<>(Set.of(1L, 2L)))
//            .build();
//        CouponType couponType = mock(CouponType.class);
//        CouponStatus couponStatus = mock(CouponStatus.class);
//        Category category = mock(Category.class);
//        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
//        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));
//        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
//
//        // When
//        couponService.createCategoryCoupon(categoryCouponCreateRequestDto);
//
//        // Then
//        verify(couponRepository, times(1)).saveAndFlush(any(Coupon.class));
//        verify(couponTypeRepository, times(1)).findById(anyInt());
//        verify(couponStatusRepository, times(1)).findById(anyInt());
//    }
//
//    @Test
//    @DisplayName("카테고리 쿠폰생성 실패")
//    void createCategoryCoupon_Fail() {
//        // Given
//        CategoryCouponCreateRequestDto categoryCouponCreateRequestDto = CategoryCouponCreateRequestDto.builder()
//            .couponName("Test Coupon")
//            .couponTypeId(1)
//            .basicIssuedDate(LocalDate.now())
//            .basicExpiredDate(LocalDate.now().plusDays(7))
//            .discountPrice(10L)
//            .maxDiscountPrice(100L)
//            .minPrice(50L)
//            .validity(30)
//            .categoryIds(new HashSet<>(Set.of(1L)))
//            .build();
//        CouponType couponType = mock(CouponType.class);
//        CouponStatus couponStatus = mock(CouponStatus.class);
//
//        // 카테고리를 찾을 수 없도록 빈 Optional을 반환하도록 설정합니다.
//        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
//
//        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
//        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));
//
//        // When, Then
//        assertThrows(NotFoundException.class, () -> {
//            couponService.createCategoryCoupon(categoryCouponCreateRequestDto);
//        });
//
//        // categoryRepository.findById가 한 번 호출되었는지 확인합니다.
//        verify(categoryRepository, times(1)).findById(1L);
//
//        // couponRepository.saveAndFlush가 호출되지 않았는지 확인합니다.
//        verify(couponRepository, times(1)).saveAndFlush(any());
//        verify(couponRepository, never()).save(any());
//
//    }
//
//    @Test
//    @DisplayName("책 쿠폰생성 성공")
//    void createBookCoupon() {
//        // Given
//        BookCouponCreateRequestDto bookCouponCreateRequestDto = BookCouponCreateRequestDto.builder()
//            .couponName("Test Coupon")
//            .couponTypeId(1)
//            .basicIssuedDate(LocalDate.now())
//            .basicExpiredDate(LocalDate.now().plusDays(7))
//            .discountPrice(10L)
//            .maxDiscountPrice(100L)
//            .minPrice(50L)
//            .validity(30)
//            .bookIds(new HashSet<>(Set.of(1L, 2L)))
//            .build();
//        CouponType couponType = mock(CouponType.class);
//        CouponStatus couponStatus = mock(CouponStatus.class);
//        Book book = mock(Book.class);
//        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
//        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));
//        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
//
//        // When
//        couponService.createBookCoupon(bookCouponCreateRequestDto);
//
//        // Then
//        verify(couponRepository, times(1)).saveAndFlush(any(Coupon.class));
//        verify(couponTypeRepository, times(1)).findById(anyInt());
//        verify(couponStatusRepository, times(1)).findById(anyInt());
//    }
//
//    @Test
//    @DisplayName("책 쿠폰생성 실패")
//    void createBookCoupon_Fail() {
//        // Given
//        BookCouponCreateRequestDto bookCouponCreateRequestDto = BookCouponCreateRequestDto.builder()
//            .couponName("Test Coupon")
//            .couponTypeId(1)
//            .basicIssuedDate(LocalDate.now())
//            .basicExpiredDate(LocalDate.now().plusDays(7))
//            .discountPrice(10L)
//            .maxDiscountPrice(100L)
//            .minPrice(50L)
//            .validity(30)
//            .bookIds(new HashSet<>(Set.of(1L)))
//            .build();
//        CouponType couponType = mock(CouponType.class);
//        CouponStatus couponStatus = mock(CouponStatus.class);
//
//        // 카테고리를 찾을 수 없도록 빈 Optional을 반환하도록 설정합니다.
//        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
//
//        when(couponTypeRepository.findById(any())).thenReturn(Optional.of(couponType));
//        when(couponStatusRepository.findById(any())).thenReturn(Optional.of(couponStatus));
//
//        // When, Then
//        assertThrows(NotFoundException.class, () -> {
//            couponService.createBookCoupon(bookCouponCreateRequestDto);
//        });
//
//        // categoryRepository.findById가 한 번 호출되었는지 확인합니다.
//        verify(bookRepository, times(1)).findById(1L);
//
//        // couponRepository.saveAndFlush가 호출되지 않았는지 확인합니다.
//        verify(couponRepository, times(1)).saveAndFlush(any());
//        verify(couponRepository, never()).save(any());
//
//    }
//
//    @Test
//    void testIssueCoupon() {
//        // Given
//        Coupon coupon = mock(Coupon.class);
//        Member member = mock(Member.class);
//        CouponIssueRequestDto issueCouponDto = mock(CouponIssueRequestDto.class);
//
//        LocalDate startDate = LocalDate.now().minusDays(1);
//        LocalDate endDate = LocalDate.now().plusDays(30);
//
//        when(coupon.getValidity()).thenReturn(30);
//        when(coupon.getBasicIssuedDate()).thenReturn(startDate);
//        when(coupon.getBasicExpiredDate()).thenReturn(endDate);
//
//        when(issueCouponDto.getCouponId()).thenReturn("asd");
//        when(issueCouponDto.getMemberId()).thenReturn(1L);
//
//        when(couponRepository.findById("asd")).thenReturn(Optional.of(coupon));
//        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
//
//        couponService.issueCoupon(issueCouponDto);
//
//        verify(memberCouponRepository, times(1)).saveAndFlush(any(MemberCoupon.class));
//    }
//
//    @Test
//    void adminViewCoupons() {
//        // Given
//        int couponStatusId = 1;
//        Pageable pageable = mock(Pageable.class);
//        Page<CouponReadResponseDto> page = mock(Page.class);
//        CouponStatus couponStatus = mock(CouponStatus.class);
//        when(couponStatusRepository.findById(couponStatusId)).thenReturn(Optional.of(couponStatus));
//        when(couponRepository.findByCouponStatus(any(CouponStatus.class),
//            any(Pageable.class))).thenReturn(page);
//
//        // When
//        couponService.adminViewCoupons(pageable, couponStatusId);
//
//        // Then
//        verify(couponRepository, times(1)).findByCouponStatus(any(CouponStatus.class),
//            any(Pageable.class));
//    }
//}