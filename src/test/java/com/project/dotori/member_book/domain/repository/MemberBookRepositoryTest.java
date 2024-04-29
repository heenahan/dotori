package com.project.dotori.member_book.domain.repository;

import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.BookReview;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class MemberBookRepositoryTest {

    @Autowired
    private MemberBookRepository memberBookRepository;

    @DisplayName("연도별 총 독서 권수와 총 독서 페이지 수를 계산한다.")
    @Test
    void calculateTotalBookAndPage() {
        // given
        var memberId = 1L;
        var year = 2024;
        var memberBooks = createMemberBooks(memberId);
        memberBookRepository.saveAll(memberBooks);

        // when
        var response = memberBookRepository.calculateTotalBookAndPage(memberId, year);

        // then
        assertThat(response).extracting(
            "totalReadCount",
                "totalPage"
        ).containsExactly(
            (long) memberBooks.size(),
            memberBooks.stream()
                .map(MemberBook::getBookReview)
                .mapToLong(BookReview::getPage)
                .sum()
        );
    }

    @DisplayName("월별 독서량을 계산한다.")
    @Test
    void calculateMonthlyCount() {
        // given
        var memberId = 1L;
        var year = 2024;
        var memberBooks = createMemberBooks(memberId);
        memberBookRepository.saveAll(memberBooks);

        // when
        var responses = memberBookRepository.calculateMonthlyCount(memberId, year);

        // then
        assertThat(responses).hasSize(12);

        var januaryOptional = responses.stream()
            .filter(res -> Objects.equals(res.getMonths(), 1))
            .findFirst();
        assertThat(januaryOptional).isNotEmpty();
        var january = januaryOptional.get();
        assertThat(january.getCnt()).isEqualTo(0);

        var marchOptional = responses.stream()
            .filter(res -> Objects.equals(res.getMonths(), 4))
            .findFirst();
        assertThat(marchOptional).isNotEmpty();
        var march = marchOptional.get();
        assertThat(march.getCnt()).isEqualTo(2);
    }

    @DisplayName("별점 별 비율을 계산한다.")
    @Test
    void calculateStarRatio() {
        // given
        var memberId = 1L;
        var year = 2024;
        var memberBooks = createMemberBooks(memberId);
        memberBookRepository.saveAll(memberBooks);

        // when
        var responses = memberBookRepository.calculateStarRatio(memberId, year);

        // then
        assertThat(responses).hasSize(11);

        var twoOptional = responses.stream()
            .filter(res -> Objects.equals(res.getStar(), 2.0f))
            .findFirst();
        assertThat(twoOptional).isNotEmpty();
        var two = twoOptional.get();
        assertThat(two.getPercentage()).isEqualTo(0);

        var threeOptional = responses.stream()
            .filter(res -> Objects.equals(res.getStar(), 3.0f))
            .findFirst();
        assertThat(threeOptional).isNotEmpty();
        var three = threeOptional.get();
        assertThat(three.getPercentage()).isEqualTo(33);

        var fourOptional = responses.stream()
            .filter(res -> Objects.equals(res.getStar(), 4.0f))
            .findFirst();
        assertThat(fourOptional).isNotEmpty();
        var four = fourOptional.get();
        assertThat(four.getPercentage()).isEqualTo(67);
    }

    @DisplayName("별점 평균을 계산한다.")
    @Test
    void calculateStarAverage() {
        // given
        var memberId = 1L;
        var year = 2024;
        var memberBooks = createMemberBooks(memberId);
        memberBookRepository.saveAll(memberBooks);

        // when
        var response = memberBookRepository.calculateStarAverage(memberId, year);

        // then
        assertThat(response.starAverage()).isEqualTo(3.7d);
    }

     private List<MemberBook> createMemberBooks(
        Long memberId
    ) {
        var memberBook1 = MemberBook.builder()
            .bookId("1234")
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 3, 10))
            .endDate(LocalDate.of(2024, 3, 31))
            .page(300)
            .totalPage(300)
            .star(4.0f)
            .build();

        var memberBook2 = MemberBook.builder()
            .bookId("2345")
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 5))
            .endDate(LocalDate.of(2024, 4, 25))
            .page(400)
            .totalPage(400)
            .star(3.0f)
            .build();

        var memberBook3 = MemberBook.builder()
            .bookId("4567")
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 14))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(500)
            .totalPage(500)
            .star(4.0f)
            .bookLevel(BookLevel.DIFFICULT)
            .build();

        return List.of(memberBook1, memberBook2, memberBook3);
    }
}