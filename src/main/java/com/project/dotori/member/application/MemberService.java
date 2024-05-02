package com.project.dotori.member.application;

import com.project.dotori.member.application.request.MemberCreateRequest;
import com.project.dotori.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long createMember(
        MemberCreateRequest request
    ) {
        var member = request.toEntity();
        var savedMember = memberRepository.findBySocialIdAndRole(member.getSocialId(), member.getRole())
            .orElseGet(() -> memberRepository.save(member));

        return savedMember.getId();
    }
}
