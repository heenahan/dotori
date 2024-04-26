package com.project.dotori.member_book.domain;

import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberBookTest {

    @DisplayName("독서 기록의 주인이 아니면 예외가 발생한다.")
    @Test
    void validOwner() {
        // given
        var owner = 1L;
        var notOwner = 2L;
        var memberBook = createMemberBook(owner);

        // when & then
        assertThatThrownBy(() -> memberBook.validOwner(notOwner))
            .isInstanceOf(BusinessException.class);
    }

    private MemberBook createMemberBook(
        Long memberId
    ) {
        return MemberBook.builder()
            .bookId("1234")
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 1))
            .endDate(LocalDate.of(2024, 4, 20))
            .totalPage(300)
            .build();
    }
}