package com.project.dotori.book.application.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookDetailResponse(
    String imagePath,
    String title,
    String author,
    String publisher,
    String isbn13,
    String categoryName,
    LocalDate publishDate,
    Integer page,
    String description,
    String link
) {
}
