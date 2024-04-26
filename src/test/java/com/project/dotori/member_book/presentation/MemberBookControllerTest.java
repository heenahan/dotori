package com.project.dotori.member_book.presentation;

import com.project.dotori.RestDocsSupport;
import com.project.dotori.global.util.SliceConverter;
import com.project.dotori.member_book.application.MemberBookService;
import com.project.dotori.member_book.application.request.MemberBookCreateServiceRequest;
import com.project.dotori.member_book.application.request.MemberBookUpdateServiceRequest;
import com.project.dotori.member_book.application.response.MemberBookCreateResponse;
import com.project.dotori.member_book.application.response.MemberBookDetailResponse;
import com.project.dotori.member_book.application.response.MemberBookResponse;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.presentation.request.MemberBookCreateRequest;
import com.project.dotori.member_book.presentation.request.MemberBookUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberBookController.class)
class MemberBookControllerTest extends RestDocsSupport {

    @MockBean
    private MemberBookService memberBookService;

    @Override
    protected Object setController() {
        return new MemberBookController(memberBookService);
    }

    @DisplayName("회원의 독서 기록을 저장한다.")
    @Test
    void createMemberBook() throws Exception {
        // given
        var memberBookId = 1L;
        var request = createMemberBookCreateRequest();
        var response = new MemberBookCreateResponse(memberBookId);
        given(memberBookService.createMemberBook(anyLong(), any(MemberBookCreateServiceRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/member-books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", "/api/v1/member-books/" + memberBookId))
            .andDo(document("create-member-book",
                requestFields(
                    fieldWithPath("isbn").type(JsonFieldType.STRING).description("책의 isbn"),
                    fieldWithPath("startDate").type(JsonFieldType.STRING).description("독서 시작 기간"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).description("독서 종료 기간"),
                    fieldWithPath("page").type(JsonFieldType.NUMBER).description("독서 페이지 수"),
                    fieldWithPath("star").type(JsonFieldType.NUMBER).description("도서 평점"),
                    fieldWithPath("bookLevel").type(JsonFieldType.STRING).description("도서 난이도"),
                    fieldWithPath("memberBookStatus").type(JsonFieldType.STRING).description("독서 상태")
                ),
                responseHeaders(
                    headerWithName("Location").description("자원 생성 위치")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.memberBookId").type(JsonFieldType.NUMBER).description("독서 기록 아이디")
                )
            ));
    }

    @DisplayName("회원의 독서 기록을 수정한다.")
    @Test
    void updateMemberBook() throws Exception {
        // given
        var memberBookId = 1L;
        var request = createMemberUpdateRequest();
        doNothing().when(memberBookService).updateMemberBook(anyLong(), any(MemberBookUpdateServiceRequest.class));

        // when & then
        mockMvc.perform(patch("/api/v1/member-books/{memberBookId}", memberBookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document("update-member-book",
                pathParameters(
                    parameterWithName("memberBookId").description("독서 기록 아이디")
                ),
                requestFields(
                    fieldWithPath("startDate").type(JsonFieldType.STRING).description("독서 시작 기간"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).description("독서 종료 기간"),
                    fieldWithPath("page").type(JsonFieldType.NUMBER).description("독서 페이지 수"),
                    fieldWithPath("star").type(JsonFieldType.NUMBER).description("도서 평점"),
                    fieldWithPath("bookLevel").type(JsonFieldType.STRING).description("도서 난이도"),
                    fieldWithPath("memberBookStatus").type(JsonFieldType.STRING).description("독서 상태")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                    fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                )
            ));
    }

    @DisplayName("상태로 독서 기록을 조회한다.")
    @Test
    void searchBooks() throws Exception {
        // given
        var pageRequest = PageRequest.of(0, 20);
        var memberBookStatus = "READ";
        var memberBookResponses = createMemberBookResponses();
        given(memberBookService.findAll(anyLong(), anyString(), any(Pageable.class)))
            .willReturn(SliceConverter.toSlice(memberBookResponses, pageRequest));

        // when & then
        mockMvc.perform(get("/api/v1/member-books")
                .param("status", memberBookStatus)
                .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andDo(document("find-member-books",
                queryParameters(
                    parameterWithName("status").description("독서 기록 상태")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("조회된 독서 기록"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("책의 제목"),
                    fieldWithPath("data.content[].author").type(JsonFieldType.STRING).description("책의 저자"),
                    fieldWithPath("data.content[].totalPage").type(JsonFieldType.NUMBER).description("책의 총 페이지 수"),
                    fieldWithPath("data.content[].coverPath").type(JsonFieldType.STRING).description("책의 이미지 경로"),
                    fieldWithPath("data.content[].memberBookId").type(JsonFieldType.NUMBER).description("독서 기록 아이디"),
                    fieldWithPath("data.content[].memberBookStatus").type(JsonFieldType.STRING).description("독서 기록 상태"),
                    fieldWithPath("data.content[].startDate").optional().description("독서 시작 날짜"),
                    fieldWithPath("data.content[].endDate").optional().description("독서 종료 날짜"),
                    fieldWithPath("data.content[].star").type(JsonFieldType.NUMBER).description("평점"),
                    fieldWithPath("data.content[].page").type(JsonFieldType.NUMBER).description("독서한 페이지 수"),
                    fieldWithPath("data.content[].percent").type(JsonFieldType.NUMBER).description("독서 비율"),
                    fieldWithPath("data.content[].bookLevel").optional().description("책의 난이도"),
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

    @DisplayName("자세한 독서 기록을 조회한다.")
    @Test
    void findDetailOne() throws Exception {
        // given
        var memberBookId = 1L;
        var response = createMemberBookDetailResponse();
        given(memberBookService.findOne(anyLong())).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/member-books/{memberBookId}", memberBookId)
                .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andDo(document("find-member-book",
                pathParameters(
                    parameterWithName("memberBookId").description("독서 기록 아이디")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.isbn").type(JsonFieldType.STRING).description("ISBN 번호"),
                    fieldWithPath("data.coverPath").type(JsonFieldType.STRING).description("책 표지 이미지 경로"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("책 제목"),
                    fieldWithPath("data.author").type(JsonFieldType.STRING).description("저자"),
                    fieldWithPath("data.publisher").type(JsonFieldType.STRING).description("출판사"),
                    fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                    fieldWithPath("data.categoryName").type(JsonFieldType.STRING).description("책 카테고리"),
                    fieldWithPath("data.memberBookStatus").type(JsonFieldType.STRING).description("독서 기록 상태"),
                    fieldWithPath("data.startDate").type(JsonFieldType.STRING).description("독서 시작 날짜").optional(),
                    fieldWithPath("data.endDate").type(JsonFieldType.STRING).description("독서 완료 날짜").optional(),
                    fieldWithPath("data.star").type(JsonFieldType.NUMBER).description("책의 평점"),
                    fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                    fieldWithPath("data.percent").type(JsonFieldType.NUMBER).description("읽은 비율(%)"),
                    fieldWithPath("data.bookLevel").type(JsonFieldType.STRING).description("책 수준").optional()
                )
            ));
    }

    private MemberBookCreateRequest createMemberBookCreateRequest() {
        return MemberBookCreateRequest.builder()
            .isbn("1234")
            .startDate(LocalDate.of(2024, 4, 1))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(200)
            .star(4.0f)
            .memberBookStatus("READ")
            .bookLevel("MEDIUM")
            .build();
    }

    private MemberBookUpdateRequest createMemberUpdateRequest() {
        return MemberBookUpdateRequest.builder()
            .startDate(LocalDate.of(2024, 4, 1))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(200)
            .star(4.0f)
            .memberBookStatus("READ")
            .bookLevel("EASY")
            .build();
    }

    private MemberBookDetailResponse createMemberBookDetailResponse() {
        return MemberBookDetailResponse.builder()
            .isbn("1234")
            .title("즐거운 영어")
            .author("김작가")
            .categoryName("국내 도서>교육>영어")
            .publisher("조은교육")
            .totalPage(300)
            .coverPath("https://")
            .memberBookStatus("READ")
            .startDate(LocalDate.of(2024, 4, 14))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(300)
            .percent(100)
            .star(4.0f)
            .bookLevel("EASY")
            .build();
    }

    private List<MemberBookResponse> createMemberBookResponses() {
        var memberBookResponse1 = MemberBookResponse.builder()
            .title("title")
            .author("author")
            .coverPath("https://")
            .totalPage(300)
            .memberBookId(1L)
            .memberBookStatus(MemberBookStatus.TO_READ)
            .page(0)
            .percent(0)
            .star(0.0f)
            .startDate(null)
            .endDate(null)
            .bookLevel(null)
            .build();

        var memberBookResponse2 = MemberBookResponse.builder()
            .title("title")
            .author("author")
            .coverPath("https://")
            .totalPage(360)
            .memberBookId(2L)
            .memberBookStatus(MemberBookStatus.READING)
            .page(36)
            .percent(10)
            .star(0.0f)
            .startDate(LocalDate.of(2024, 4, 20))
            .endDate(null)
            .bookLevel(null)
            .build();

        var memberBookResponse3 = MemberBookResponse.builder()
            .title("title")
            .author("author")
            .coverPath("https://")
            .totalPage(287)
            .memberBookId(3L)
            .memberBookStatus(MemberBookStatus.READ)
            .page(287)
            .percent(100)
            .star(4.0f)
            .startDate(LocalDate.of(2024, 4, 6))
            .endDate(LocalDate.of(2024, 4, 25))
            .bookLevel(null)
            .build();

        return List.of(memberBookResponse1, memberBookResponse2, memberBookResponse3);
    }
}