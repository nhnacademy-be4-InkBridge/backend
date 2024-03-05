package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.repository.custom.MemberCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: MemberRepository.
 *
 * @author minseo
 * @version 2/15/24
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    boolean existsByEmail(String email);

}