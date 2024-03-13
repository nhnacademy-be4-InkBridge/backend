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

    /**
     * 사용자 ID에 해당하는 모든 주소 정보를 조회합니다.
     *
     * @param userId 사용자 ID (요청 헤더 'Authorization-Id'로 전달)
     * @return 조회된 주소 정보 리스트와 HTTP 상태 코드 200
     */
    @GetMapping
    ResponseEntity<List<MemberAddressReadResponseDto>> getAddresses(
        @RequestHeader("Authorization-Id") Long userId) {
        return ResponseEntity.ok(memberAddressService.getAddressByUserId(userId));
    }

    /**
     * 특정 주소 ID에 해당하는 주소 정보를 조회합니다.
     *
     * @param userId 사용자 ID (요청 헤더 'Authorization-Id'로 전달)
     * @param addressId 조회할 주소 ID
     * @return 조회된 주소 정보와 HTTP 상태 코드 200
     */
    @GetMapping("/{addressId}")
    ResponseEntity<MemberAddressReadResponseDto> getAddress(
        @RequestHeader("Authorization-Id") Long userId, @PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok(memberAddressService.getAddressByUserIdAndAddressId(userId, addressId));
    }

    /**
     * 새로운 주소 정보를 생성합니다.
     *
     * @param userId 사용자 ID (요청 헤더 'Authorization-Id'로 전달)
     * @param addressCreateRequestDto 생성할 주소의 정보
     * @return HTTP 상태 코드 200
     */
    @PostMapping
    ResponseEntity<HttpStatus> createAddress(@RequestHeader("Authorization-Id") Long userId,
        @RequestBody AddressCreateRequestDto addressCreateRequestDto) {
        memberAddressService.createAddress(userId, addressCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 기존 주소 정보를 수정합니다.
     *
     * @param userId 사용자 ID (요청 헤더 'Authorization-Id'로 전달)
     * @param addressUpdateRequestDto 수정할 주소의 정보
     * @return HTTP 상태 코드 200
     */
    @PutMapping
    ResponseEntity<HttpStatus> modifyAddress(@RequestHeader("Authorization-Id") Long userId,
        @RequestBody AddressUpdateRequestDto addressUpdateRequestDto) {
        memberAddressService.updateAddress(userId, addressUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 특정 주소 정보를 삭제합니다.
     *
     * @param userId 사용자 ID (요청 헤더 'Authorization-Id'로 전달)
     * @param addressId 삭제할 주소 ID
     * @return HTTP 상태 코드 200
     */
    @DeleteMapping("/{addressId}")
    ResponseEntity<HttpStatus> deleteAddress(@RequestHeader("Authorization-Id") Long userId,
        @PathVariable("addressId") Long addressId) {
        memberAddressService.deleteAddress(userId, addressId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
