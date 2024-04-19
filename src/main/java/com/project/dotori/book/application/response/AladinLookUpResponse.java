package com.project.dotori.book.application.response;

import java.time.LocalDate;

public record AladinLookUpResponse(
    String title,
    String link,
    String author,
    String description,
    LocalDate pubDate,
    String isbn13,
    String cover,
    Long categoryId,
    String publisher,
    AladinSubInfo subInfo
) {

    public BookDetailResponse toBookDetailResponse() {
        return BookDetailResponse.builder()
            .imagePath(cover)
            .title(title)
            .page(subInfo().itemPage)
            .author(author)
            .publisher(publisher)
            .isbn13(isbn13)
            .description(description)
            .link(link)
            .build();
    }

    record AladinSubInfo(
        Integer itemPage
    ) {}
}
