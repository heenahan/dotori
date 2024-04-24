package com.project.dotori.book.application;

import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import com.project.dotori.global.util.SliceConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookReader bookReader;

    public BookDetailResponse findBookDetail(
        String isbn
    ) {
        return bookReader.findBookDetail(isbn);
    }

    public Slice<BookSearchResponse> searchBooks(
        String query,
        Pageable pageable
    ) {
        var bookSearchResponses = bookReader.searchBooks(query, pageable);

        return SliceConverter.toSlice(bookSearchResponses, pageable);
    }
}
