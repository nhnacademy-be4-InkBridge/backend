package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.address.AddressCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.MemberAddressReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import java.util.List;

/**
 * class: MemberAddressService.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */
public interface MemberAddressService {

    List<MemberAddressReadResponseDto> getAddressByUserId(Long userId);

    void createAddress(Long userId, AddressCreateRequestDto addressCreateRequestDto);

    void updateAddress(Long userId, AddressUpdateRequestDto addressUpdateRequestDto);

    void deleteAddress(Long userId, Long addressId);
}
