package com.project.dotori.member_book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
        LocalDate startDate,
        LocalDate endDate,
        Integer page,
        Integer totalPage,
        Float star,
        BookLevel bookLevel
    ) {
        this.memberBookId = memberBookId;
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
}
