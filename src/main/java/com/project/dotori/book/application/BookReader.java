package com.project.dotori.book.application;

import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class BookReader {

    private static final String NOT_FOUND_BOOK = "책을 찾을 수 없습니다. 옳바르지 못한 isbn13입니다. value = %s";

    private final BookApiService bookApiService;

    public BookDetailResponse findBookDetail(
        String isbn
    ) {
        return bookApiService.findBookDetail(isbn)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND_BOOK.formatted(isbn)));
    }

    public BookDetailResponse findBookDetailAsync(
        String isbn
    ) {
        var bookDetailResponse = CompletableFuture.supplyAsync(() -> bookApiService.findBookDetail(isbn))
            .join();

        return bookDetailResponse.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND_BOOK.formatted(isbn)));
    }

    public List<BookSearchResponse> searchBooks(
        String query,
        Pageable pageable
    ) {
        return bookApiService.searchBooks(query, pageable);
    }
}
