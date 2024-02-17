package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: PointPolicyTypeRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/17
 */
@NoRepositoryBean
public interface PointPolicyTypeRepositoryCustom {

    List<PointPolicyTypeReadResponseDto> findAllPointPolicyTypeBy();

}
