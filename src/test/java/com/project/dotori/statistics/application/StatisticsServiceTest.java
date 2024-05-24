package com.project.dotori.statistics.application;

import com.project.dotori.book.domain.Book;
import com.project.dotori.book.domain.BookCategory;
import com.project.dotori.book.domain.repository.BookCategoryRepository;
import com.project.dotori.book.domain.repository.BookRepository;
import com.project.dotori.member.domain.Member;
import com.project.dotori.member.domain.Role;
import com.project.dotori.member.domain.repository.MemberRepository;
import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import com.project.dotori.statistics.application.response.StatisticsCategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class StatisticsServiceTest {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private MemberBookRepository memberBookRepository;

    @DisplayName("멤버의 독서 기록에 대해 연간 통계를 조회한다.")
    @Test
    void findStatisticsYear() {
        // given
        var year = 2024;
        var member = createMember();
        memberRepository.save(member);
        var bookCategories = createBookCategories();
        bookCategoryRepository.saveAll(bookCategories);
        var books = createBooks(bookCategories);
        bookRepository.saveAll(books);
        var memberBooks = createMemberBooks(books, member.getId());
        memberBookRepository.saveAll(memberBooks);

        // when
        var response = statisticsService.findStatisticsYear(member.getId(), year);

        // then
        assertThat(response).extracting(
            "totalReadCount",
            "bookHeight"
        ).containsExactly(4L, 7L);
        var monthly = response.monthly();
        assertThat(monthly).extracting(
            "mostReadMonth.month",
            "mostReadMonth.count",
            "averageReadMonth"
        ).containsExactly(4, 2, 0.3d);
        assertThat(monthly.monthlyCounts()).hasSize(12);
        var star = response.star();
        assertThat(star.ratios()).hasSize(11);
        var categories = response.categories();
        assertThat(categories.ratios())
            .hasSize(3)
            .isSortedAccordingTo(comparing(StatisticsCategoryResponse.CategoryRatio::count).reversed());
    }

    private Member createMember() {
        return Member.builder()
            .socialId("1234")
            .email("abc@abc.com")
            .nickname("nickname")
            .role(Role.GOOGLE)
            .build();
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
            .page(500)
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
            .page(200)
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
            .totalPage(books.get(0).getBookBasicInfo().getPage())
            .star(4.0f)
            .build();

        var memberBook2 = MemberBook.builder()
            .bookId(books.get(1).getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 5))
            .endDate(LocalDate.of(2024, 4, 25))
            .page(400)
            .totalPage(books.get(1).getBookBasicInfo().getPage())
            .star(3.0f)
            .build();

        var memberBook3 = MemberBook.builder()
            .bookId(books.get(2).getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 4, 14))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(500)
            .totalPage(books.get(2).getBookBasicInfo().getPage())
            .star(4.0f)
            .bookLevel(BookLevel.HARD)
            .build();

        var memberBook4 = MemberBook.builder()
            .bookId(books.get(3).getIsbn())
            .memberId(memberId)
            .memberBookStatus(MemberBookStatus.READ)
            .startDate(LocalDate.of(2024, 2, 14))
            .endDate(LocalDate.of(2024, 2, 28))
            .page(200)
            .totalPage(books.get(3).getBookBasicInfo().getPage())
            .star(0.0f)
            .bookLevel(BookLevel.EASY)
            .build();

        return List.of(memberBook1, memberBook2, memberBook3, memberBook4);
    }
}