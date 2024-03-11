package com.project.dotori.book;

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

    private static final String INVALID_ISBN = "Isbn은 13자 이하여야 합니다. length = %d";
    private static final String INVALID_TITLE = "제목은 50자 이하여야 합니다. length = %d";
    private static final String INVALID_AUTHOR = "작가 이름은 50자 이하여야 합니다. length = %d";
    private static final String INVALID_COVER_PATH = "표지 경로는 100자 이하여야 합니다. length = %d";
    private static final String INVALID_PAGE = "페이지 수는 0이상 이여야 합니다. value = %d";
    private static final String INVALID_PRICE = "가격은 0이상 이여야 합니다. value = %d";
    private static final String INVALID_DESCRIPTION = "설명은 1000자 이하여야 합니다. length = %d";

    @Column(name = "isbn", length = 13, nullable = false)
    private String isbn;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @Column(name = "cover_path", length = 100, nullable = false)
    private String coverPath;

    @Column(name = "page", nullable = false)
    private Integer page;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Builder
    private BookBasicInfo(
        String isbn,
        String title,
        String author,
        String coverPath,
        Integer page,
        Integer price,
        String description
    ) {
        validLength(isbn, title, author, coverPath, description);
        validNumber(page, price);
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.coverPath = coverPath;
        this.page = page;
        this.price = price;
        this.description = description;
    }

    private void validLength(
        String isbn,
        String title,
        String author,
        String coverPath,
        String description
    ) {
        if (StringUtils.isBlank(isbn) || isbn.length() > 13) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_ISBN.formatted(StringUtils.length(isbn)));
        }
        if (StringUtils.isBlank(title) || title.length() > 50) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_TITLE.formatted(StringUtils.length(title)));
        }
        if (StringUtils.isBlank(author) || author.length() > 50) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_AUTHOR.formatted(StringUtils.length(author)));
        }
        if (StringUtils.isBlank(coverPath) || coverPath.length() > 100) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_COVER_PATH.formatted(StringUtils.length(coverPath)));
        }
        if (StringUtils.isBlank(description) || description.length() > 1000) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_DESCRIPTION.formatted(StringUtils.length(description)));
        }
    }

    private void validNumber(
        Integer page,
        Integer price
    ) {
        if (page == null || page < 0) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_PAGE.formatted(page));
        }
        if (price == null || price < 0) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_PRICE.formatted(price));
        }
    }
}
