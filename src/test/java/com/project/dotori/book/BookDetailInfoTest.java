package com.project.dotori.book;

import com.project.dotori.StringRandomGenerator;
import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookDetailInfoTest {

    @DisplayName("coverPath가 null이거나 빈칸이어도 책 상세정보가 생성된다.")
    @NullSource
    @ValueSource(strings = { "", " " })
    @ParameterizedTest
    void validCoverPath(String coverPath) {
        // when
        var bookDetailInfo = BookDetailInfo.builder()
            .coverPath(coverPath)
            .description("description")
            .build();

        // then
        assertThat(bookDetailInfo.getCoverPath()).isEqualTo(coverPath);
    }

    @DisplayName("coverPath가 100자를 초과하면 예외가 발생한다.")
    @Test
    void validCoverPath() {
        // given
        var coverPath = StringRandomGenerator.generate(101);

        // when & then
        assertThatThrownBy(() ->
                BookDetailInfo.builder()
                    .coverPath(coverPath)
                    .description("description")
                    .build())
            .isInstanceOf(BusinessException.class);
    }

    @DisplayName("description이 null이거나 빈칸이어도 책 상세정보가 생성된다.")
    @NullSource
    @ValueSource(strings = { "", " " })
    @ParameterizedTest
    void validDescription(String description) {
        // when
        var bookDetailInfo = BookDetailInfo.builder()
            .coverPath("https://")
            .description(description)
            .build();

        // then
        assertThat(bookDetailInfo.getDescription()).isEqualTo(description);
    }

    @DisplayName("description이 500자를 초과하면 예외가 발생한다.")
    @Test
    void validDescription() {
        // given
        var description = StringRandomGenerator.generate(501);

        // when & then
        assertThatThrownBy(() ->
                BookDetailInfo.builder()
                    .coverPath("https://")
                    .description(description)
                    .build())
            .isInstanceOf(BusinessException.class);
    }
}