package com.nhnacademy.inkbridge.backend.dto.address;

import com.nhnacademy.inkbridge.backend.entity.MemberAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: AddressReadResponseDto.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */
@NoArgsConstructor
@Getter
public class MemberAddressReadResponseDto {

    private Long addressId;

    private String zipCode;

    private String address;

    private String alias;

    private String addressDetail;

    public static MemberAddressReadResponseDto toDto(MemberAddress memberAddress) {
        return MemberAddressReadResponseDto.builder()
            .address(memberAddress.getGeneralAddress().getAddress())
            .zipCode(memberAddress.getGeneralAddress().getZipCode()).addressId(
                memberAddress.getAddressId()).alias(memberAddress.getAlias())
            .addressDetail(memberAddress.getAddressDetail()).build();
    }

    @Builder
    public MemberAddressReadResponseDto(Long addressId, String zipCode, String address,
        String alias,
        String addressDetail) {
        this.addressId = addressId;
        this.zipCode = zipCode;
        this.address = address;
        this.alias = alias;
        this.addressDetail = addressDetail;
    }
}
