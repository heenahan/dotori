package com.project.dotori.book.application.response;

import lombok.Builder;

@Builder
public record BookDetailResponse(
    String imagePath,
    String title,
    String author,
    String publisher,
    String isbn13,
    Integer page,
    String description,
    String link
) {
}
