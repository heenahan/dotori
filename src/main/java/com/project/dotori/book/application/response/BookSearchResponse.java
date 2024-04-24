package com.project.dotori.book.application.response;

import lombok.Builder;

@Builder
public record BookSearchResponse(
    String coverPath,
    String title,
    String author,
    String publisher
) {
}
