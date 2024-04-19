package com.project.dotori.book.presentation;

import com.project.dotori.book.application.BookService;
import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import com.project.dotori.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/{isbn}")
    public ResponseEntity<ApiResponse<BookDetailResponse>> findBookDetail(
        @PathVariable String isbn
    ) {
        var response = bookService.findBookDetail(isbn);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Slice<BookSearchResponse>>> searchBook(
        @RequestParam("search") String search,
        @PageableDefault Pageable pageable
    ) {
        var responses = bookService.searchBook(search, pageable);

        return ResponseEntity.ok(ApiResponse.ok(responses));
    }
}
