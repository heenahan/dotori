package com.project.dotori.book.infrastructure.openfeign.response;

import com.project.dotori.book.application.response.BookDetailResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AladinLookUpResponse(
    String title,
    String link,
    String author,
    String description,
    LocalDate pubDate,
    String isbn13,
    String cover,
    Long categoryId,
    String categoryName,
    String publisher,
    AladinSubInfo subInfo
) {

    public BookDetailResponse toBookDetailResponse() {
        return BookDetailResponse.builder()
            .coverPath(cover)
            .title(title)
            .page(subInfo().itemPage)
            .author(author)
            .publisher(publisher)
            .isbn13(isbn13)
            .categoryId(categoryId)
            .categoryName(categoryName)
            .publishDate(pubDate)
            .description(description)
            .link(link)
            .build();
    }

    public record AladinSubInfo(
        Integer itemPage
    ) {}
}
