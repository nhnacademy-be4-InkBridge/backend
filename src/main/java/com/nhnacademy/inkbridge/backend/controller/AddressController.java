package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.address.AddressCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.AddressUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.address.MemberAddressReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.MemberAddressService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AdressController.
 *
 * @author jeongbyeonghun
 * @version 3/8/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage/address")
public class AddressController {

    private final MemberAddressService memberAddressService;

    @GetMapping
    ResponseEntity<List<MemberAddressReadResponseDto>> getAddress(
        @RequestHeader("Authorization-Id") Long userId) {
        return ResponseEntity.ok(memberAddressService.getAddressByUserId(userId));
    }

    @PostMapping
    ResponseEntity<HttpStatus> createAddress(@RequestHeader("Authorization-Id") Long userId,
        @RequestBody AddressCreateRequestDto addressCreateRequestDto) {
        memberAddressService.createAddress(userId, addressCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    ResponseEntity<HttpStatus> modifyAddress(@RequestHeader("Authorization-Id") Long userId,
        @RequestBody AddressUpdateRequestDto addressUpdateRequestDto) {
        memberAddressService.updateAddress(userId, addressUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{addressId}")
    ResponseEntity<HttpStatus> deleteAddress(@RequestHeader("Authorization-Id") Long userId,
        @PathVariable("addressId") Long addressId) {
        memberAddressService.deleteAddress(userId, addressId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
