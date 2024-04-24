package com.project.dotori.member_book.application;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberBookReader {

    private static final String NOT_FOUND = "도서 기록을 찾을 수 없습니다. value = %d";
    private final MemberBookRepository memberBookRepository;

    public MemberBook findOne(
        Long memberBookId
    ) {
        return memberBookRepository.findById(memberBookId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND.formatted(memberBookId)));
    }
}
