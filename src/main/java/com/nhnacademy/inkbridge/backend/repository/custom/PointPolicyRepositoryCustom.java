package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: PointPolicyRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
@NoRepositoryBean
public interface PointPolicyRepositoryCustom {
    List<PointPolicyReadResponseDto> findAllPointPolicyBy();

    List<PointPolicyReadResponseDto> findAllPointPolicyByTypeId(Integer pointPolicyTypeId);

    List<PointPolicyReadResponseDto> findAllCurrentPointPolicies();

    PointPolicyReadResponseDto findCurrentPointPolicy(Integer pointPolicyTypeId);
}
