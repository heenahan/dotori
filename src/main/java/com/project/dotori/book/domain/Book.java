package com.project.dotori.book.domain;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "books")
@Entity
public class Book {

    private static final String INVALID_ISBN = "isbn은 13자 이하여야 합니다. length = %d";

    @Id
    @Column(name = "isbn", length = 13, nullable = false)
    private String isbn;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Embedded
    private BookBasicInfo bookBasicInfo;

    @Embedded
    private PublishInfo publishInfo;

    @Embedded
    private BookDetailInfo bookDetailInfo;

    @Builder
    private Book (
        String isbn,
        Long categoryId,
        String title,
        String author,
        Integer page,
        String publisher,
        LocalDate publishDate,
        String coverPath,
        String description
    ) {
        validIsbn(isbn);

        this.isbn = isbn;
        this.categoryId = categoryId;
        this.bookBasicInfo = BookBasicInfo.builder()
            .title(title)
            .author(author)
            .page(page)
            .build();
        this.publishInfo = PublishInfo.builder()
            .publisher(publisher)
            .publishDate(publishDate)
            .build();
        this.bookDetailInfo = BookDetailInfo.builder()
            .coverPath(coverPath)
            .description(description)
            .build();
    }

    public void updateBookDetailInfo(
        String coverPath,
        String description
    ) {
        this.bookDetailInfo = BookDetailInfo.builder()
            .coverPath(coverPath)
            .description(description)
            .build();
    }

    private void validIsbn(
        String isbn
    ) {
        if (StringUtils.isBlank(isbn) || isbn.length() > 13) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_ISBN.formatted(StringUtils.length(isbn)));
        }
    }
}
