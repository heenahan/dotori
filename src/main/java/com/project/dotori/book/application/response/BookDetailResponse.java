package com.project.dotori.book.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dotori.book.domain.Book;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookDetailResponse(
    String coverPath,
    String title,
    String author,
    String publisher,
    String isbn13,
    Long categoryId,
    String categoryName,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate publishDate,
    Integer page,
    String description,
    String link
) {

    public Book toEntity() {
        return Book.builder()
            .isbn(isbn13)
            .title(title)
            .author(author)
            .publisher(publisher)
            .categoryId(categoryId)
            .publishDate(publishDate)
            .page(page)
            .coverPath(coverPath)
            .description(description)
            .build();
    }
}
