package com.project.dotori.book.application;

import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookApiService {

    Optional<BookDetailResponse> findBookDetail(String isbn13);

    List<BookSearchResponse> searchBooks(String query, Pageable pageable);
}
