package com.project.dotori.member_book.domain.repository;

import com.project.dotori.book.domain.Book;
import com.project.dotori.book.domain.BookCategory;
import com.project.dotori.book.domain.repository.BookCategoryRepository;
import com.project.dotori.book.domain.repository.BookRepository;
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

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private BookRepository bookRepository;

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

    @DisplayName("연도별 총 독서 권수와 총 독서 페이지 수를 계산할 때 독서 기록이 없어 null이면 0을 반환한다.")
    @Test
    void calculateTotalBookAndPageZero() {
        // given
        var memberId = 1L;
        var year = 2024;

        // when
        var response = memberBookRepository.calculateTotalBookAndPage(memberId, year);

        // then
        assertThat(response.totalReadCount()).isZero();
        assertThat(response.totalPage()).isZero();
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
        assertThat(two.getPercentage()).isZero();

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

    @DisplayName("별정 평균을 계산할 때 독서 기록이 없어 null이면 0으로 표시된다.")
    @Test
    void calculateStarAverageZero() {
        // given
        var memberId = 1L;
        var year = 2024;

        // when
        var response = memberBookRepository.calculateStarAverage(memberId, year);

        // then
        assertThat(response.starAverage()).isEqualTo(0.0d);
    }

    @DisplayName("멤버의 독서 기록에서 Top5 카테고리의 수와 비율을 계산한다.")
    @Test
    void calculateTopCategory() {
        // given
        var memberId = 1L;
        var year = 2024;
        var bookCategories = createBookCategories();
        bookCategoryRepository.saveAll(bookCategories);
        var books = createBooks(bookCategories);
        bookRepository.saveAll(books);
        var memberBooks = createMemberBooks(books, memberId);
        memberBookRepository.saveAll(memberBooks);

        // when
        var responses = memberBookRepository.calculateTopCategory(memberId, year);

        // then
        assertThat(responses).hasSize(3);
        assertThat(responses.get(0)).extracting(
            "categoryId",
            "categoryName",
            "cnt",
            "percentage"
        ).containsExactly(
            bookCategories.get(0).getId(),
            bookCategories.get(0).getName(),
            2L,
            50L
        );
        assertThat(responses.get(1)).extracting(
            "categoryId",
            "categoryName",
            "cnt",
            "percentage"
        ).containsExactly(
            bookCategories.get(1).getId(),
            bookCategories.get(1).getName(),
            1L,
            25L
        );
    }

    private List<BookCategory> createBookCategories() {
        var bookCategory1 = BookCategory.builder()
            .name("문학")
            .build();

        var bookCategory2 = BookCategory.builder()
            .name("컴퓨터공학")
            .build();

        var bookCategory3 = BookCategory.builder()
            .name("서양고대사")
            .build();

        return List.of(bookCategory1, bookCategory2, bookCategory3);
    }

    private List<Book> createBooks(
        List<BookCategory> categories
    ) {
        var book1 = Book.builder()
            .isbn("1234")
            .title("title")
            .author("author")
            .coverPath("https://")
            .publishDate(LocalDate.of(2023, 1, 1))
            .categoryId(categories.get(0).getId())
            .page(300)
            .description("description")
            .publisher("publisher")
            .build();

        var book2 = Book.builder()
            .isbn("2345")
            .title("title")
            .author("author")
            .coverPath("https://")
            .publishDate(LocalDate.of(2022, 12, 15))
            .categoryId(categories.get(0).getId())
            .page(400)
            .description("description")
            .publisher("publisher")
            .build();

        var book3 = Book.builder()
            .isbn("3456")
            .title("title")
            .author("author")
            .coverPath("https://")
            .publishDate(LocalDate.of(2020, 10, 20))
            .categoryId(categories.get(1).getId())
            .page(360)
            .description("description")
            .publisher("publisher")
            .build();

        var book4 = Book.builder()
            .isbn("4567")
            .title("title")
            .author("author")
            .coverPath("https://")
            .publishDate(LocalDate.of(2020, 10, 20))
            .categoryId(categories.get(2).getId())
            .page(500)
            .description("description")
            .publisher("publisher")
            .build();

        return List.of(book1, book2, book3, book4);
    }

    private List<MemberBook> createMemberBooks(
        List<Book> books,
        Long memberId
    ) {
        var memberBook1 = MemberBook.builder()
            .bookId(books.get(0).getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 3, 10))
            .endDate(LocalDate.of(2024, 3, 31))
            .page(300)
            .totalPage(300)
            .star(4.0f)
            .build();

        var memberBook2 = MemberBook.builder()
            .bookId(books.get(1).getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 5))
            .endDate(LocalDate.of(2024, 4, 25))
            .page(400)
            .totalPage(400)
            .star(3.0f)
            .build();

        var memberBook3 = MemberBook.builder()
            .bookId(books.get(2).getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 14))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(500)
            .totalPage(500)
            .star(4.0f)
            .bookLevel(BookLevel.DIFFICULT)
            .build();

        var memberBook4 = MemberBook.builder()
            .bookId(books.get(3).getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 2, 14))
            .endDate(LocalDate.of(2024, 2, 28))
            .page(278)
            .totalPage(278)
            .star(0.0f)
            .bookLevel(BookLevel.EASY)
            .build();

        return List.of(memberBook1, memberBook2, memberBook3, memberBook4);
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