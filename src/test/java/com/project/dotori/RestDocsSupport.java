package com.project.dotori;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.dotori.authorization.presentation.interceptor.AuthorizationInterceptor;
import com.project.dotori.authorization.application.jwt.JwtGenerator;
import com.project.dotori.authorization.presentation.handler.MemberIdArgumentResolver;
import com.project.dotori.global.exception.GlobalExceptionHandler;
import com.project.dotori.member.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension.class)
@Import(JwtTestConfiguration.class)
public abstract class RestDocsSupport {

    private static final String TOKEN = "Bearer %s";

    @Autowired
    private JwtGenerator jwtGenerator;

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    protected abstract Object setController();

    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(setController())
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .setControllerAdvice(GlobalExceptionHandler.class)
            .addMappedInterceptors(new String[]{
                "/api/v1/books/**",
                "/api/v1/member-books/**",
                "/api/v1/statistics/**"
            }, new AuthorizationInterceptor(jwtGenerator))
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(), new MemberIdArgumentResolver())
            .build();
    }

    protected String getToken(
        Long memberId
    ) {
        return TOKEN.formatted(jwtGenerator.generateAccessToken(memberId, Role.GOOGLE));
    }
}
