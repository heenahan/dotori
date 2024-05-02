package com.project.dotori.member.domain.repository;

import com.project.dotori.member.domain.Member;
import com.project.dotori.member.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialIdAndRole(
        String socialId,
        Role roe
    );
}
