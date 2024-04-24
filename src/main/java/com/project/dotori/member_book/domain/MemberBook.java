package com.project.dotori.member_book.domain;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_books")
@Entity
public class MemberBook {

    private static final String NOT_OWNER = "독서 기록의 소유자가 일치하지 않습니다. owner = %d";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "book_id", nullable = false)
    private String bookId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private MemberBookStatus memberBookStatus;

    @Embedded
    private ReadingDate readingDate;

    @Embedded
    private BookReview bookReview;

    @Builder
    private MemberBook(
        Long memberId,
        String bookId,
        MemberBookStatus memberBookStatus,
        LocalDate startDate,
        LocalDate endDate,
        int page,
        int totalPage,
        Float star,
        BookLevel bookLevel
    ) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.memberBookStatus = memberBookStatus;
        this.readingDate = ReadingDate.builder()
            .startDate(startDate)
            .endDate(endDate)
            .build();
        this.bookReview = BookReview.builder()
            .page(page)
            .totalPage(totalPage)
            .star(star)
            .bookLevel(bookLevel)
            .build();
    }

    public void updateMemberBook(
        Long memberId,
        MemberBook memberBook
    ) {
        validOwner(memberId);
        this.memberBookStatus = memberBook.getMemberBookStatus();
        this.readingDate = memberBook.getReadingDate();
        this.bookReview = memberBook.getBookReview();
    }

    public void validOwner(
        Long memberId
    ) {
        if (!Objects.equals(memberId, this.memberId)) {
            throw new BusinessException(ErrorCode.NOT_OWNER, NOT_OWNER.formatted(memberId));
        }
    }

    public boolean isToRead() {
        return this.memberBookStatus == MemberBookStatus.TO_READ;
    }
}
