package com.project.dotori.member_book.application;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberBookValidator {

    private static final String INVALID_MEMBER_BOOK = "동일한 책에 대한 기록이 있습니다. memberId = %d, isbn = %s";
    private final MemberBookRepository memberBookRepository;

    public void validMemberBook(
        Long memberId,
        String isbn
    ) {
        var memberBook = memberBookRepository.findByMemberIdAndBookId(memberId, isbn);
        if (memberBook.isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATED, INVALID_MEMBER_BOOK.formatted(memberId, isbn));
        }
    }
}
