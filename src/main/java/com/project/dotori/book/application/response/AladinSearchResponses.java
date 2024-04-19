package com.project.dotori.book.application.response;

import java.util.List;

public record AladinSearchResponses(
    List<AladinSearchResponse> item
) {

    public List<BookSearchResponse> toBookSearchResponses() {
        return item.stream()
            .map(AladinSearchResponse::toBookSearchResponse)
            .toList();
    }
}
