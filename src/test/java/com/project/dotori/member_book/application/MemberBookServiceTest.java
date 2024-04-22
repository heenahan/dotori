package com.project.dotori.member_book.application;

import com.project.dotori.book.domain.Book;
import com.project.dotori.book.domain.repository.BookRepository;
import com.project.dotori.member_book.application.request.MemberBookServiceRequest;
import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.MemberBookRecordRepository;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberBookServiceTest {

    @Autowired
    private MemberBookService memberBookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberBookRepository memberBookRepository;

    @Autowired
    private MemberBookRecordRepository memberBookRecordRepository;

    @DisplayName("읽음과 읽는 중 상태일 때 MemberBook과 MemberBookRecord를 저장한다.")
    @MethodSource("memberBookServiceRequests")
    @ParameterizedTest
    void createMemberBook(
        MemberBookServiceRequest request
    ) {
        // given
        var memberId = 1L;
        var book = createBook(request.isbn());
        bookRepository.save(book);

        // when
        var response = memberBookService.createMemberBookRead(memberId, request);

        // then
        var memberBookOptional = memberBookRepository.findById(response.memberBookId());
        assertThat(memberBookOptional).isNotEmpty();
        var memberBook = memberBookOptional.get();
        assertThat(memberBook).extracting(
            "memberId",
            "bookId",
            "memberBookStatus"
        ).containsExactly(memberId, book.getIsbn(), request.memberBookStatus());
        var memberBookRecordOptional = memberBookRecordRepository.findByMemberBookId(response.memberBookId());
        assertThat(memberBookRecordOptional).isNotEmpty();
        var memberBookRecord = memberBookRecordOptional.get();
        assertThat(memberBookRecord).extracting(
            "memberBookId",
            "readingDate.startDate",
            "readingDate.endDate",
            "bookReview.page",
            "bookReview.star",
            "bookReview.bookLevel"
        ).containsExactly(response.memberBookId(), request.startDate(), request.endDate(), request.page(), request.star(), request.bookLevel());
    }

    @DisplayName("읽을 예정 상태일 때 MemberBook만 저장한다.")
    @Test
    void createMemberBookToRead() {
        // given
        var memberId = 1L;
        var request = createMemberBookServiceRequest();
        var book = createBook(request.isbn());
        bookRepository.save(book);

        // when
        var response = memberBookService.createMemberBookRead(memberId, request);

        // then
        var memberBookOptional = memberBookRepository.findById(response.memberBookId());
        assertThat(memberBookOptional).isNotEmpty();
        var memberBook = memberBookOptional.get();
        assertThat(memberBook).extracting(
            "memberId",
            "bookId",
            "memberBookStatus"
        ).containsExactly(memberId, book.getIsbn(), request.memberBookStatus());
        var memberBookRecordOptional = memberBookRecordRepository.findByMemberBookId(response.memberBookId());
        assertThat(memberBookRecordOptional).isEmpty();
    }

    private Book createBook(
        String isbn
    ) {
        return Book.builder()
            .isbn(isbn)
            .title("title")
            .author("author")
            .coverPath("https://")
            .publishDate(LocalDate.of(2023, 1, 1))
            .categoryId(1L)
            .page(300)
            .description("description")
            .publisher("publisher")
            .build();
    }

    private MemberBookServiceRequest createMemberBookServiceRequest() {
        return new MemberBookServiceRequest("1234", null, null, 0, null, null, MemberBookStatus.TO_READ);

    }

    private static Stream<Arguments> memberBookServiceRequests() {
        return Stream.of(
            Arguments.arguments(
                new MemberBookServiceRequest("1234", LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 20), 200, 4.5f, BookLevel.EASY, MemberBookStatus.READ)
            ),
            Arguments.arguments(
                new MemberBookServiceRequest("1234", LocalDate.of(2024, 4, 1), null, 100, null, null, MemberBookStatus.READING)
            )
        );
    }
}