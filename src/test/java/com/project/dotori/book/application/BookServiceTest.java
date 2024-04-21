package com.project.dotori.book.application;

import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookApiService bookApiService;

    @DisplayName("도서를 검색한다.")
    @Test
    void searchBooks() {
        // given
        var pageRequest = PageRequest.of(1, 20);
        var query = "대규모";

        var bookSearchResponse1 = createBookSearchResponse();
        var bookSearchResponse2 = createBookSearchResponse();
        var bookSearchResponses = List.of(bookSearchResponse1, bookSearchResponse2);
        given(bookApiService.searchBooks(anyString(), any(Pageable.class))).willReturn(bookSearchResponses);

        // when
        var bookSearchResponsesSlice = bookService.searchBooks(query, pageRequest);

        // then
        assertThat(bookSearchResponsesSlice.getContent()).hasSize(2);
        assertThat(bookSearchResponsesSlice.hasNext()).isFalse();
    }

    @DisplayName("isbn으로 도서를 상세조회한다.")
    @Test
    void findBookDetail() {
        // given
        var isbn13 = "1234";
        var bookDetailResponse = createBookDetailResponse(isbn13);
        given(bookApiService.findBookDetail(anyString())).willReturn(Optional.of(bookDetailResponse));

        // when
        var bookServiceBookDetail = bookService.findBookDetail(isbn13);

        // then
        assertThat(bookServiceBookDetail).extracting(
            "imagePath",
            "title",
            "author",
            "publisher",
            "isbn13",
            "page",
            "description",
            "link",
            "publishDate",
            "categoryName"
        ).containsExactly(
            bookDetailResponse.coverPath(),
            bookDetailResponse.title(),
            bookDetailResponse.author(),
            bookDetailResponse.publisher(),
            bookDetailResponse.isbn13(),
            bookDetailResponse.page(),
            bookDetailResponse.description(),
            bookDetailResponse.link(),
            bookDetailResponse.publishDate(),
            bookDetailResponse.categoryName()
        );
    }

    private BookSearchResponse createBookSearchResponse() {
        return BookSearchResponse.builder()
            .title("title")
            .author("author")
            .coverPath("https://")
            .publisher("publisher")
            .build();
    }

    private BookDetailResponse createBookDetailResponse(
        String isbn13
    ) {
        return BookDetailResponse.builder()
            .title("title")
            .author("author")
            .isbn13(isbn13)
            .categoryName("국내 도서 > 컴퓨터 공학")
            .coverPath("https://")
            .description("description")
            .publisher("publisher")
            .publishDate(LocalDate.of(2023, 1, 1))
            .link("https://")
            .page(200)
            .build();
    }
}