package com.project.dotori.authorization.presentation;

import com.project.dotori.RestDocsSupport;
import com.project.dotori.authorization.application.AuthorizationService;
import com.project.dotori.authorization.application.jwt.Jwt;
import com.project.dotori.authorization.application.request.GoogleCodeServiceRequest;
import com.project.dotori.authorization.application.response.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorizationController.class)
class AuthorizationControllerTest extends RestDocsSupport {

    @MockBean
    private AuthorizationService authorizationService;

    @Override
    protected Object setController() {
        return new AuthorizationController(authorizationService);
    }

    @DisplayName("구글로 소셜 로그인을 한다.")
    @Test
    void loginGoogle() throws Exception {
        // given
        var code = "abcd/adcvw";
        var response = new LoginResponse("eyabcde", Jwt.AUTH_TYPE.getDescription());

        given(authorizationService.login(any(GoogleCodeServiceRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/auth/login/google")
                .contentType(MediaType.APPLICATION_JSON)
                .param("code", code)
            ).andDo(print())
            .andExpect(status().isOk())
            .andDo(document("login-google",
                queryParameters(
                    parameterWithName("code").description("구글로 부터 받은 코드")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("dotori 내에서 사용하는 access token"),
                    fieldWithPath("data.tokenType").type(JsonFieldType.STRING).description("토큰 타입")
                )
            ));
    }
}