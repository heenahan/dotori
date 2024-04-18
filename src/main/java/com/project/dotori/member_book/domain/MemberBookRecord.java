package com.project.dotori.member_book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_book_records")
@Entity
public class MemberBookRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_book_id", nullable = false)
    private Long memberBookId;

    @Embedded
    private ReadingDate readingDate;

    @Embedded
    private BookReview bookReview;

    @Builder
    private MemberBookRecord(
        Long memberBookId,
        ReadingDate readingDate,
        BookReview bookReview
    ) {
        this.memberBookId = memberBookId;
        this.readingDate = readingDate;
        this.bookReview = bookReview;
    }
}
