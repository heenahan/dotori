package com.project.dotori.member.application;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import com.project.dotori.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberValidator {

    private static final String NOT_FOUND = "존재하지 않는 멤버입니다. value = %d";

    private final MemberRepository memberRepository;

    public void validMember (
        Long memberId
    ) {
        var member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND.formatted(memberId));
        }
    }
}
