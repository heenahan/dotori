package com.project.dotori.book.application.response;

import lombok.Builder;

@Builder
public record BookSearchResponse(
    String imagePath,
    String title,
    String author,
    String publisher
) {
}
