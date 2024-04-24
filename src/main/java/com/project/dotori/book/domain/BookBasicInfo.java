package com.project.dotori.book.domain;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BookBasicInfo {

    private static final String INVALID_TITLE = "제목은 50자 이하여야 합니다. length = %d";
    private static final String INVALID_AUTHOR = "작가 이름은 50자 이하여야 합니다. length = %d";
    private static final String INVALID_PAGE = "페이지 수는 0이상 이여야 합니다. value = %d";

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @Column(name = "page", nullable = false)
    private Integer page = 0;

    @Builder
    private BookBasicInfo(
        String title,
        String author,
        Integer page
    ) {
        validLength(title, author);
        validNumber(page);
        this.title = title;
        this.author = author;
        this.page = page;
    }

    private void validLength(
        String title,
        String author
    ) {
        if (StringUtils.isBlank(title) || title.length() > 50) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_TITLE.formatted(StringUtils.length(title)));
        }
        if (StringUtils.isBlank(author) || author.length() > 50) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_AUTHOR.formatted(StringUtils.length(author)));
        }
    }

    private void validNumber(
        Integer page
    ) {
        if (page == null || page < 0) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_PAGE.formatted(page));
        }
    }
}
