package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import java.util.List;

/**
 * class: PointPolicyTypeService.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
public interface PointPolicyTypeService {

    List<PointPolicyTypeReadResponseDto> getPointPolicyTypes();

    void createPointPolicyType(
        PointPolicyTypeCreateRequestDto pointPolicyTypeCreateRequestDto);

    void updatePointPolicyType(
        PointPolicyTypeUpdateRequestDto pointPolicyTypeUpdateRequestDto);

    void deletePointPolicyTypeById(Integer pointPolicyTypeId);
}
