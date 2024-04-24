package com.project.dotori.member_book.application;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberBookValidatorTest {

    @Autowired
    private MemberBookValidator memberBookValidator;

    @Autowired
    private MemberBookRepository memberBookRepository;

    @DisplayName("동일한 책에 대한 기록이 있으면 예외가 발생한다.")
    @Transactional
    @Test
    void duplicatedMemberBook() {
        // given
        var memberId = 1L;
        var isbn = "1234";
        var memberBook = MemberBook.builder()
            .memberId(memberId)
            .bookId(isbn)
            .memberBookStatus(MemberBookStatus.TO_READ)
            .build();
        memberBookRepository.save(memberBook);

        // when & then
        assertThatThrownBy(() -> memberBookValidator.validMemberBook(memberId, isbn))
            .isInstanceOf(BusinessException.class);
    }

}