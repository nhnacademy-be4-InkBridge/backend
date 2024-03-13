package com.nhnacademy.inkbridge.backend.dto.address;

import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: AddressUpdateRequestDto.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */
@NoArgsConstructor
@Setter
@Getter
public class AddressUpdateRequestDto {

    private Long addressId;

    private String zipCode;

    private String address;

    private String alias;

    private String addressDetail;

    private String receiverName;

    private String receiverNumber;

    public GeneralAddress toGeneralAddress() {
        return GeneralAddress.builder().address(address).zipCode(zipCode).build();
    }
}
