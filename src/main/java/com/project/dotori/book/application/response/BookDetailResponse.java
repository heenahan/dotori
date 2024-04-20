package com.project.dotori.book.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate publishDate,
    Integer page,
    String description,
    String link
) {
}
