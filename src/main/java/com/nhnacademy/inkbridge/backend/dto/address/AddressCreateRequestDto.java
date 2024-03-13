package com.nhnacademy.inkbridge.backend.dto.address;

import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: AddressCreateRequestDto.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */

@NoArgsConstructor
@Getter
@Setter
public class AddressCreateRequestDto {

    private String zipCode;

    private String address;

    private String alias;

    private String addressDetail;

    private String receiverName;

    private String receiverNumber;


    public MemberAddress toEntity(Member user, GeneralAddress generalAddress) {
        return MemberAddress.builder().generalAddress(generalAddress).member(user)
            .addressDetail(addressDetail).alias(alias).receiverName(receiverName)
            .receiverNumber(receiverNumber).build();
    }

    public GeneralAddress toGeneralAddress() {
        return GeneralAddress.builder().address(address).zipCode(zipCode).build();
    }
}
