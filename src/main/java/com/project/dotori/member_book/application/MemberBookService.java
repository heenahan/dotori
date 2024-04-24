package com.project.dotori.member_book.application;

import com.project.dotori.book.application.BookReader;
import com.project.dotori.book.domain.Book;
import com.project.dotori.book.domain.repository.BookRepository;
import com.project.dotori.member_book.application.request.MemberBookServiceRequest;
import com.project.dotori.member_book.application.response.MemberBookResponse;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.repository.MemberBookRecordRepository;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberBookService {

    private final BookRepository bookRepository;
    private final BookReader bookReader;
    private final MemberBookValidator memberBookValidator;
    private final MemberBookRepository memberBookRepository;
    private final MemberBookRecordRepository memberBookRecordRepository;

    @Transactional
    public MemberBookResponse createMemberBookRead(
        Long memberId,
        MemberBookServiceRequest request
    ) {
        memberBookValidator.validMemberBook(memberId, request.isbn());

        // 도서 정보 확인
        var book = findBook(request.isbn());

        // 회원 도서 저장
        var memberBook = request.toEntity(memberId);
        memberBookRepository.save(memberBook);

        // 도서 기록 저장
        createMemberBookRecord(memberBook, book, request);

        return MemberBookResponse.from(memberBook);
    }

    private Book findBook(
        String isbn
    ) {
        var book = bookRepository.findById(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        var bookDetailResponse = bookReader.findBookDetailAsync(isbn);
        return bookRepository.save(bookDetailResponse.toEntity());
    }

    private void createMemberBookRecord(
        MemberBook memberBook,
        Book book,
        MemberBookServiceRequest request
    ) {
        if (!memberBook.isToRead()) {
            var page = book.getBookBasicInfo().getPage();
            var memberBookRecord = request.toEntity(memberBook.getId(), page);
            memberBookRecordRepository.save(memberBookRecord);
        }
    }
}
