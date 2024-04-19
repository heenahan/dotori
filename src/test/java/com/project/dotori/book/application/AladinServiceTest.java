package com.project.dotori.book.application;

import com.project.dotori.global.config.CustomPropertiesConfig;
import com.project.dotori.global.config.OpenFeignConfig;
import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import({
    OpenFeignConfig.class,
    CustomPropertiesConfig.class,
    FeignAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class
})
class AladinServiceTest {

    @Autowired
    private AladinService aladinService;

    @DisplayName("알라딘 API를 통해 도서를 검색한다.")
    @Test
    void searchBook() {
        // given
        var query = "대규모";
        var pageRequest = PageRequest.of(1, 20);

        // when
        var aladinSearchResponses = aladinService.searchBookByAladin(query, pageRequest);

        // then
        assertThat(aladinSearchResponses.item()).isNotEmpty();
    }

    @DisplayName("알라딘 API를 통해 도서 상세 정보를 조회한다.")
    @Test
    void findBookDetail() {
        // given
        var isbn13 = "9788966263158";

        // when
        var aladinLookUpResponse = aladinService.findBookDetailByAladin(isbn13);

        // then
        assertThat(aladinLookUpResponse).isNotNull();
    }

    @DisplayName("존재하지 않는 ISBN13을 이용하여 도서 상세 정보 조회시 예외가 발생한다.")
    @Test
    void findBookDetailByInvalidIsbn() {
        // given
        var isbn13 = "WIRED_ISBN";

        // when & then
        assertThatThrownBy(() -> aladinService.findBookDetailByAladin(isbn13))
            .isInstanceOf(BusinessException.class);
    }
}