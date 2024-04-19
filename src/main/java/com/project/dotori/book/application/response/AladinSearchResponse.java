package com.project.dotori.book.application.response;

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
            .imagePath(cover)
            .author(author)
            .title(title)
            .description(description)
            .publisher(publisher)
            .build();
    }
}
