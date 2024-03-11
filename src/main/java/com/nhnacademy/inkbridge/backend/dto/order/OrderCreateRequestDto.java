package com.nhnacademy.inkbridge.backend.dto.order;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: OrderCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private List<BookOrderDetailCreateRequestDto> bookOrderList;
    private BookOrderCreateRequestDto bookOrder;

    @Getter
    @NoArgsConstructor
    public static class BookOrderDetailCreateRequestDto {

        private Long bookId;
        private Long price;
        private Integer amount;
        private Long wrappingId;
        private String couponId;
        private Long wrappingPrice;
    }

    @Getter
    @NoArgsConstructor
    public static class BookOrderCreateRequestDto {

        @NotBlank(message = "주문 이름은 필수 항목입니다.")
        private String orderName;
        @NotBlank(message = "수취인 이름은 필수 항목입니다.")
        private String receiverName;
        @NotBlank(message = "수취인 전화번호는 필수 항목입니다.")
        private String receiverPhoneNumber;
        @NotBlank(message = "우편 번호는 필수 항목입니다.")
        private String zipCode;
        @NotBlank(message = "주소는 필수 항목입니다.")
        private String address;
        @NotBlank(message = "상세 주소는 필수 항목입니다.")
        private String detailAddress;
        @NotBlank(message = "주문인 이름은 필수 항목입니다.")
        private String senderName;
        @NotBlank(message = "주문인 전화번호는 필수 항목입니다.")
        private String senderPhoneNumber;
        @NotBlank(message = "주문인 이메일은 필수 항목입니다.")
        private String senderEmail;
        @NotNull(message = "배송 예정일은 필수 항목입니다.")
        private LocalDate deliveryDate;
        @Min(value = 0, message = "포인트는 음수가 될 수 없습니다.")
        private Long usePoint;
        @Min(value = 0, message = "결제 금액은 음수가 될 수 없습니다.")
        private Long payAmount;
        private Long memberId;
        @Min(value = 0, message = "배송비는 음수가 될 수 없습니다.")
        private Long deliveryPrice;
    }
}
