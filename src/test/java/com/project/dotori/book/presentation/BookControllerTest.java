package com.project.dotori.book.presentation;

import com.project.dotori.RestDocsSupport;
import com.project.dotori.book.application.BookService;
import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import com.project.dotori.global.util.SliceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest extends RestDocsSupport {

    @MockBean
    private BookService bookService;


    @Override
    protected Object setController() {
        return new BookController(bookService);
    }

    @DisplayName("isbn으로 책을 상세 조회한다.")
    @Test
    void findBookDetail() throws Exception {
        // given
        var isbn = "1234";
        var bookDetailResponse = BookDetailResponse.builder()
            .title("영어가 쉽다!")
            .author("김작가")
            .description("영어를 쉽게 배울 수 있는 기회!")
            .publishDate(LocalDate.of(2023, 1, 1))
            .link("https://")
            .coverPath("https://")
            .page(300)
            .categoryId(1L)
            .categoryName("국내도서>영어")
            .isbn13(isbn)
            .publisher("조은교육")
            .build();
        given(bookService.findBookDetail(anyString())).willReturn(bookDetailResponse);

        // when & then
        mockMvc.perform(get("/api/v1/books/{isbn}", isbn)
            .header(HttpHeaders.AUTHORIZATION, getToken(1L))
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk())
        .andDo(document("find-book-detail",
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("isbn").description("책의 Isbn13")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("책의 제목"),
                fieldWithPath("data.author").type(JsonFieldType.STRING).description("책의 저자"),
                fieldWithPath("data.description").type(JsonFieldType.STRING).description("책의 설명"),
                fieldWithPath("data.publishDate").type(JsonFieldType.STRING).description("책의 출판일"),
                fieldWithPath("data.link").type(JsonFieldType.STRING).description("책의 구매 링크"),
                fieldWithPath("data.coverPath").type(JsonFieldType.STRING).description("책의 이미지 경로"),
                fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("책의 페이지 수"),
                fieldWithPath("data.categoryId").type(JsonFieldType.NUMBER).description("책의 카테고리 아이디"),
                fieldWithPath("data.categoryName").type(JsonFieldType.STRING).description("책의 카테고리 이름"),
                fieldWithPath("data.isbn13").type(JsonFieldType.STRING).description("책의 Isbn13"),
                fieldWithPath("data.publisher").type(JsonFieldType.STRING).description("책의 출판사")
            )
        ));
    }

    @DisplayName("책을 검색한다.")
    @Test
    void searchBooks() throws Exception {
        // given
        var pageRequest = PageRequest.of(1, 20);
        var query = "영어";
        var bookSearchResponse1 = BookSearchResponse.builder()
            .title("영어가 쉽다!")
            .author("김작가")
            .isbn13("4567")
            .coverPath("https://")
            .publisher("조은교육")
            .build();
        var bookSearchResponse2 = BookSearchResponse.builder()
            .title("영어랑 놀자")
            .author("박작가")
            .isbn13("1234")
            .coverPath("https://")
            .publisher("행복도서")
            .build();
        var bookSearchResponses = List.of(bookSearchResponse1, bookSearchResponse2);
        given(bookService.searchBooks(anyString(), any(Pageable.class))).willReturn(SliceConverter.toSlice(bookSearchResponses, pageRequest));

        // when & then
        mockMvc.perform(get("/api/v1/books/search")
            .header(HttpHeaders.AUTHORIZATION, getToken(1L))
            .param("query", query)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk())
        .andDo(document("search-books",
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            queryParameters(
                parameterWithName("query").description("검색어")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("검색된 책 목록"),
                fieldWithPath("data.content[].isbn13").type(JsonFieldType.STRING).description("책의 isbn13"),
                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("책의 제목"),
                fieldWithPath("data.content[].author").type(JsonFieldType.STRING).description("책의 저자"),
                fieldWithPath("data.content[].coverPath").type(JsonFieldType.STRING).description("책의 이미지 경로"),
                fieldWithPath("data.content[].publisher").type(JsonFieldType.STRING).description("책의 출판사"),
                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
                    .description("현재 페이지 번호"),
                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
                    .description("페이지 크기"),
                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT)
                    .description("정렬 상태 객체"),
                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("정렬 정보가 비어있는지 여부"),
                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                    .description("정렬 정보가 있는지 여부"),
                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("정렬 정보가 정렬되지 않은지 여부"),
                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER)
                    .description("페이지 번호"),
                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
                    .description("페이징이 되어 있는지 여부"),
                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
                    .description("페이징이 되어 있지 않은지 여부"),
                fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                    .description("리스트 크기"),
                fieldWithPath("data.number").type(JsonFieldType.NUMBER)
                    .description("현재 페이지 번호"),
                fieldWithPath("data.sort").type(JsonFieldType.OBJECT)
                    .description("정렬 정보 객체"),
                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("정렬 정보가 비어있는지 여부"),
                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN)
                    .description("정렬 정보가 있는지 여부"),
                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("정렬 정보가 정렬되지 않은지 여부"),
                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("현재 페이지의 요소 수"),
                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN)
                    .description("첫 번째 페이지인지 여부"),
                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN)
                    .description("마지막 페이지인지 여부"),
                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN)
                    .description("비어있는지 여부")
            )
        ));
    }
}