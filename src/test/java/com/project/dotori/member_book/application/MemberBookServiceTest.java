package com.project.dotori.member_book.application;

import com.project.dotori.book.domain.Book;
import com.project.dotori.book.domain.repository.BookRepository;
import com.project.dotori.member_book.application.request.MemberBookCreateServiceRequest;
import com.project.dotori.member_book.application.request.MemberBookUpdateServiceRequest;
import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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

    @DisplayName("읽는 중과 읽을 예정인 멤버의 독서 기록을 저장한다.")
    @MethodSource("memberBookServiceRequests")
    @ParameterizedTest
    void createMemberBook(
        MemberBookCreateServiceRequest request
    ) {
        // given
        var memberId = 1L;
        var book = createBook(request.isbn());
        bookRepository.save(book);

        // when
        var response = memberBookService.createMemberBook(memberId, request);

        // then
        var memberBookOptional = memberBookRepository.findById(response.memberBookId());
        assertThat(memberBookOptional).isNotEmpty();
        var memberBook = memberBookOptional.get();
        assertThat(memberBook).extracting(
            "memberId",
            "bookId",
            "memberBookStatus",
            "readingDate.startDate",
            "readingDate.endDate",
            "bookReview.page",
            "bookReview.star",
            "bookReview.bookLevel"
        ).containsExactly(memberId, book.getIsbn(), request.memberBookStatus(), request.startDate(), request.endDate(), request.page(), request.star(), request.bookLevel());
    }

    @DisplayName("읽음 상태일때 멤버의 독서 기록을 저장하면 읽은 페이지 수가 책의 총 페이지 수와 같다.")
    @Test
    void createMemberBook() {
        // given
        var memberId = 1L;
        var request = new MemberBookCreateServiceRequest("1234", LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 20), 200, 4.5f, BookLevel.EASY, MemberBookStatus.READ);
        var book = createBook(request.isbn());
        bookRepository.save(book);

        // when
        var response = memberBookService.createMemberBook(memberId, request);

        // then
        var memberBookOptional = memberBookRepository.findById(response.memberBookId());
        assertThat(memberBookOptional).isNotEmpty();
        var memberBook = memberBookOptional.get();
        assertThat(memberBook).extracting(
            "memberId",
            "bookId",
            "memberBookStatus",
            "readingDate.startDate",
            "readingDate.endDate",
            "bookReview.page",
            "bookReview.star",
            "bookReview.bookLevel"
        ).containsExactly(memberId, book.getIsbn(), request.memberBookStatus(), request.startDate(), request.endDate(), book.getBookBasicInfo().getPage(), request.star(), request.bookLevel());
    }

    @DisplayName("독서 기록을 수정한다.")
    @Test
    void updateMemberBook() {
        // given
        var memberId = 1L;
        var page = 300;
        var isbn = "1234";
        var book = createBook(isbn, page);
        var memberBook = createMemberBook(book, memberId);
        bookRepository.save(book);
        var savedMemberBook = memberBookRepository.save(memberBook);
        var request = new MemberBookUpdateServiceRequest(
            savedMemberBook.getId(),
            LocalDate.of(2024, 4, 10),
            null,
            200,
            0.0f,
            null,
            MemberBookStatus.READING
        );

        // when
        memberBookService.updateMemberBook(memberId, request);

        // then
        var memberBookOptional = memberBookRepository.findById(savedMemberBook.getId());
        assertThat(memberBookOptional).isNotEmpty();
        var updatedMemberBook = memberBookOptional.get();
        assertThat(updatedMemberBook).extracting(
            "memberId",
            "bookId",
            "memberBookStatus",
            "readingDate.startDate",
            "readingDate.endDate",
            "bookReview.page",
            "bookReview.star",
            "bookReview.bookLevel"
        ).containsExactly(memberId, book.getIsbn(), request.memberBookStatus(), request.startDate(), request.endDate(), request.page(), request.star(), request.bookLevel());
    }

    @DisplayName("독서 기록을 조회한다.")
    @MethodSource("memberBookStatusAndResult")
    @ParameterizedTest
    void findAll(
        String status,
        int size
    ) {
        // given
        var memberId = 1L;
        var pageRequest = PageRequest.of(0, 20);
        var books = createBooks();
        var memberBooks = createMemberBooks(memberId);

        bookRepository.saveAll(books);
        memberBookRepository.saveAll(memberBooks);

        // when
        var responses = memberBookService.findAll(memberId, status, pageRequest);

        // then
        assertThat(responses.getContent()).hasSize(size);
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

    private Book createBook(
        String isbn,
        Integer page
    ) {
        return Book.builder()
            .isbn(isbn)
            .title("title")
            .author("author")
            .coverPath("https://")
            .publishDate(LocalDate.of(2023, 1, 1))
            .categoryId(1L)
            .page(page)
            .description("description")
            .publisher("publisher")
            .build();
    }

    private MemberBook createMemberBook(
        Book book,
        Long memberId
    ) {
        return MemberBook.builder()
            .bookId(book.getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 1))
            .endDate(LocalDate.of(2024, 4, 20))
            .totalPage(book.getBookBasicInfo().getPage())
            .build();
    }

    private List<MemberBook> createMemberBooks(
        Long memberId
    ) {
        var memberBook1 = MemberBook.builder()
            .bookId("1234")
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.TO_READ)
            .page(0)
            .totalPage(300)
            .star(0.0f)
            .build();

        var memberBook2 = MemberBook.builder()
            .bookId("2345")
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READING)
            .startDate(LocalDate.of(2024, 4, 5))
            .page(40)
            .totalPage(300)
            .star(0.0f)
            .build();

        var memberBook3 = MemberBook.builder()
            .bookId("4567")
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 14))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(300)
            .totalPage(300)
            .star(4.0f)
            .bookLevel(BookLevel.DIFFICULT)
            .build();

        return List.of(memberBook1, memberBook2, memberBook3);
    }

    private List<Book> createBooks() {
        var book1 = Book.builder()
            .isbn("1234")
            .title("title")
            .author("author")
            .coverPath("https://")
            .publisher("publisher")
            .publishDate(LocalDate.of(2023, 1, 1))
            .categoryId(1L)
            .page(300)
            .build();

        var book2 = Book.builder()
            .isbn("2345")
            .title("title")
            .author("author")
            .coverPath("https://")
            .publisher("publisher")
            .publishDate(LocalDate.of(2023, 1, 1))
            .categoryId(1L)
            .page(300)
            .build();

        var book3 = Book.builder()
            .isbn("4567")
            .title("title")
            .author("author")
            .coverPath("https://")
            .publisher("publisher")
            .publishDate(LocalDate.of(2023, 1, 1))
            .categoryId(1L)
            .page(300)
            .build();

        return List.of(book1, book2, book3);
    }

    private MemberBookCreateServiceRequest createMemberBookServiceRequest() {
        return new MemberBookCreateServiceRequest("1234", null, null, 0, 0.0f, null, MemberBookStatus.TO_READ);

    }

    private static Stream<Arguments> memberBookServiceRequests() {
        return Stream.of(
            Arguments.arguments(
                new MemberBookCreateServiceRequest("1234", LocalDate.of(2024, 4, 1), null, 100, 0.0f, null, MemberBookStatus.READING)
            ),
            Arguments.arguments(
                new MemberBookCreateServiceRequest("1234", null, null, 0, 0.0f, null, MemberBookStatus.TO_READ)
            )
        );
    }

    private static Stream<Arguments> memberBookStatusAndResult() {
        return Stream.of(
            Arguments.arguments(null, 3),
            Arguments.arguments("TO_READ", 1),
            Arguments.arguments("READING", 1),
            Arguments.arguments("READ", 1)
        );
    }
}