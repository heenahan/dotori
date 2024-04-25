package com.project.dotori.member_book.application;

import com.project.dotori.book.application.BookReader;
import com.project.dotori.book.domain.Book;
import com.project.dotori.book.domain.repository.BookRepository;
import com.project.dotori.member_book.application.request.MemberBookCreateServiceRequest;
import com.project.dotori.member_book.application.request.MemberBookUpdateServiceRequest;
import com.project.dotori.member_book.application.response.MemberBookCreateResponse;
import com.project.dotori.member_book.application.response.MemberBookResponse;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberBookService {

    private final BookRepository bookRepository;
    private final BookReader bookReader;

    private final MemberBookValidator memberBookValidator;
    private final MemberBookReader memberBookReader;
    private final MemberBookRepository memberBookRepository;

    @Transactional
    public MemberBookCreateResponse createMemberBook(
        Long memberId,
        MemberBookCreateServiceRequest request
    ) {
        memberBookValidator.validMemberBook(memberId, request.isbn());

        // 도서 정보 확인
        var book = findBook(request.isbn());

        // 회원 도서 저장
        var page = book.getBookBasicInfo().getPage();
        var memberBook = request.toEntity(memberId, page);
        memberBookRepository.save(memberBook);

        return MemberBookCreateResponse.from(memberBook);
    }

    @Transactional
    public void updateMemberBook(
        Long memberId,
        MemberBookUpdateServiceRequest request
    ) {
        var memberBook = memberBookReader.findOne(request.memberBookId());
        var book = bookReader.findOne(memberBook.getBookId());

        var page = book.getBookBasicInfo().getPage();
        var updatedMemberBook = request.toEntity(page);
        memberBook.updateMemberBook(memberId, updatedMemberBook);
    }

    public Slice<MemberBookResponse> findAll(
        Long memberId,
        String status,
        Pageable pageable
    ) {
        var memberBookStatus = Optional.ofNullable(status)
                .map(MemberBookStatus::from)
                .orElseGet(() -> null);

        var responses = memberBookReader.findAllByStatus(memberId, memberBookStatus, pageable);

        return responses.map(MemberBookResponse::from);
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
}
