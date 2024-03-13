package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: MemberCustomRepository.
 *
 * @author devminseo
 * @version 3/2/24
 */
@NoRepositoryBean
public interface MemberCustomRepository {
    MemberAuthLoginResponseDto findByMemberAuth(String email);

    Optional<MemberInfoResponseDto> findByMemberInfo(Long memberId);


}
