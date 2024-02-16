package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: PointPolicyTypeRepository.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
public interface PointPolicyTypeRepository extends JpaRepository<PointPolicyType, Integer> {

    List<PointPolicyTypeReadResponseDto> findAllPointPolicyTypeBy();

    boolean existsByPolicyType(String policyType);

    Integer countAllBy();

}
