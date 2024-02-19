package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: MemberRepository.
 *
 * @author ijeongbeom
 * @version 2024/02/19
 */
public interface MemberRepository extends JpaRepository<Member,Long> {

}
