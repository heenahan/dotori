package com.project.dotori.book.application;

import com.project.dotori.book.application.response.AladinLookUpResponse;
import com.project.dotori.book.application.response.AladinSearchResponse;
import com.project.dotori.book.application.response.AladinSearchResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private AladinService aladinService;

    @DisplayName("도서를 검색한다.")
    @Test
    void searchBooks() {
        // given
        var pageRequest = PageRequest.of(1, 20);
        var query = "대규모";

        var aladinSearchResponse1 = createAladinSearchResponse();
        var aladinSearchResponse2 = createAladinSearchResponse();
        var aladinSearchResponses = new AladinSearchResponses(List.of(aladinSearchResponse1, aladinSearchResponse2));
        given(aladinService.searchBookByAladin(anyString(), any(Pageable.class))).willReturn(aladinSearchResponses);

        // when
        var bookSearchResponses = bookService.searchBooks(query, pageRequest);

        // then
        assertThat(bookSearchResponses.getContent()).hasSize(2);
        assertThat(bookSearchResponses.hasNext()).isFalse();
    }

    @DisplayName("isbn으로 도서를 상세조회한다.")
    @Test
    void findBookDetail() {
        // given
        var isbn13 = "1234";
        var aladinLookUpResponse = createAladinLookUpResponse(isbn13);
        given(aladinService.findBookDetailByAladin(anyString())).willReturn(aladinLookUpResponse);

        // when
        var bookDetailResponse = bookService.findBookDetail(isbn13);

        // then
        assertThat(bookDetailResponse).extracting(
            "imagePath",
            "title",
            "author",
            "publisher",
            "isbn13",
            "page",
            "description",
            "link"
        ).containsExactly(
            aladinLookUpResponse.cover(),
            aladinLookUpResponse.title(),
            aladinLookUpResponse.author(),
            aladinLookUpResponse.publisher(),
            aladinLookUpResponse.isbn13(),
            aladinLookUpResponse.subInfo().itemPage(),
            aladinLookUpResponse.description(),
            aladinLookUpResponse.link()
        );
    }

    private AladinSearchResponse createAladinSearchResponse() {
        return AladinSearchResponse.builder()
            .title("title")
            .link("https://")
            .author("author")
            .pubDate(LocalDate.of(2020, 1, 1))
            .description("description")
            .isbn("1234")
            .isbn13("123456")
            .cover("https://")
            .categoryId(1L)
            .publisher("publisher")
            .build();
    }

    private AladinLookUpResponse createAladinLookUpResponse(
        String isbn13
    ) {
        return AladinLookUpResponse.builder()
            .title("title")
            .author("author")
            .isbn13(isbn13)
            .categoryId(1L)
            .cover("https://")
            .description("description")
            .publisher("publisher")
            .link("https://")
            .pubDate(LocalDate.of(2020, 1, 1))
            .subInfo(new AladinLookUpResponse.AladinSubInfo(200))
            .build();
    }
}