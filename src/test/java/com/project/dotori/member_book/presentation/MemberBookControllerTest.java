package com.project.dotori.member_book.presentation;

import com.project.dotori.RestDocsSupport;
import com.project.dotori.member_book.application.MemberBookService;
import com.project.dotori.member_book.application.request.MemberBookServiceRequest;
import com.project.dotori.member_book.application.response.MemberBookResponse;
import com.project.dotori.member_book.presentation.request.MemberBookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
        var request = createMemberBookRequest();
        var response = new MemberBookResponse(memberBookId);
        given(memberBookService.createMemberBookRead(anyLong(), any(MemberBookServiceRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/member-books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", "/api/v1/member-books/" + memberBookId))
            .andDo(document("create-member-book",
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

    private MemberBookRequest createMemberBookRequest() {
        return MemberBookRequest.builder()
            .isbn("1234")
            .startDate(LocalDate.of(2024, 4, 1))
            .endDate(LocalDate.of(2024, 4, 20))
            .page(200)
            .star(4.0f)
            .memberBookStatus("READ")
            .bookLevel("MEDIUM")
            .build();
    }
}