package com.project.dotori.member_book.domain.repository;

import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.repository.response.MemberBookDetailQueryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {

    Optional<MemberBook> findByMemberIdAndBookId(Long memberId, String bookId);

    @Query("""
        select 
            new com.project.dotori.member_book.domain.repository.response.MemberBookDetailQueryResponse(
                b.isbn, b.bookDetailInfo.coverPath, b.bookBasicInfo.title, b.bookBasicInfo.author, b.publishInfo.publisher, b.bookBasicInfo.page,
                bc.name,
                mb.memberBookStatus, mb.readingDate.startDate, mb.readingDate.endDate,
                mb.bookReview.star, mb.bookReview.page, mb.bookReview.percentage, mb.bookReview.bookLevel
            )
        from Book b
        join MemberBook mb on b.isbn = mb.bookId
        join BookCategory bc on b.categoryId = bc.id
        where mb.id = :memberBookId
    """)
    Optional<MemberBookDetailQueryResponse> findMemberBookDetailById(
        @Param("memberBookId") Long memberBookId
    );
}
