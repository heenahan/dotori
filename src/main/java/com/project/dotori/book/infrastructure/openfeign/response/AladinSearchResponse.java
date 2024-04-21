package com.project.dotori.book.infrastructure.openfeign.response;

import com.project.dotori.book.application.response.BookSearchResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AladinSearchResponse(
    String title,
    String link,
    String author,
    LocalDate pubDate,
    String description,
    String isbn,
    String isbn13,
    String cover,
    Long categoryId,
    String publisher
) {

    public BookSearchResponse toBookSearchResponse() {
        return BookSearchResponse.builder()
            .coverPath(cover)
            .author(author)
            .title(title)
            .publisher(publisher)
            .build();
    }
}
