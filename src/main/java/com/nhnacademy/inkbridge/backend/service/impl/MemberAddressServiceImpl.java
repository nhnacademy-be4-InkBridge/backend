package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.address.AddressCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.MemberAddressReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.MemberAddress;
import com.nhnacademy.inkbridge.backend.enums.AddressMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.GeneralAddressRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberAddressRepository;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.service.MemberAddressService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: MemberAddressServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 3/9/24
 */

@Service
@RequiredArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;
    private final MemberRepository memberRepository;
    private final GeneralAddressRepository generalAddressRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberAddressReadResponseDto> getAddressByUserId(Long userId) {
        return memberAddressRepository.findAllByMemberMemberId(userId).stream()
            .map(MemberAddressReadResponseDto::toDto).collect(
                Collectors.toList());
    }

    @Override
    public void createAddress(Long userId, AddressCreateRequestDto addressCreateRequestDto) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        GeneralAddress generalAddress = generalAddressRepository.findByZipCodeAndAddress(
                addressCreateRequestDto.getZipCode(), addressCreateRequestDto.getAddress())
            .orElseGet(() -> generalAddressRepository.save(addressCreateRequestDto.toGeneralAddress()));
        MemberAddress memberAddress = addressCreateRequestDto.toEntity(user, generalAddress);
        memberAddressRepository.save(memberAddress);
    }

    @Override
    public void updateAddress(Long userId, AddressUpdateRequestDto addressUpdateRequestDto) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        GeneralAddress generalAddress = generalAddressRepository.findByZipCodeAndAddress(
                addressUpdateRequestDto.getZipCode(), addressUpdateRequestDto.getAddress())
            .orElseGet(() -> generalAddressRepository.save(addressUpdateRequestDto.toGeneralAddress()));
        MemberAddress memberAddress = memberAddressRepository.findByMemberAndAddressId(user,
            addressUpdateRequestDto.getAddressId()).orElseThrow(() -> new NotFoundException(
            AddressMessageEnum.ADDRESS_NOT_FOUND_ERROR.getMessage()));

        memberAddress.update(generalAddress, addressUpdateRequestDto);
        memberAddressRepository.save(memberAddress);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        MemberAddress memberAddress = memberAddressRepository.findByMemberMemberIdAndAddressId(
            userId, addressId).orElseThrow(
            () -> new NotFoundException(AddressMessageEnum.ADDRESS_NOT_FOUND_ERROR.getMessage()));
        memberAddressRepository.delete(memberAddress);
    }
}
