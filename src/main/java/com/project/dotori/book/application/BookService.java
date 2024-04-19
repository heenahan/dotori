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

    private final AladinService aladinService;

    public BookDetailResponse findBookDetail(
        String isbn
    ) {
        var aladinLookUpResponse = aladinService.findBookDetailByAladin(isbn);

        return aladinLookUpResponse.toBookDetailResponse();
    }

    public Slice<BookSearchResponse> searchBooks(
        String query,
        Pageable pageable
    ) {
        var aladinSearchResponses = aladinService.searchBookByAladin(query, pageable);
        var bookSearchResponses = aladinSearchResponses.toBookSearchResponses();

        return SliceConverter.toSlice(bookSearchResponses, pageable);
    }
}
