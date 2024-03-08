package com.project.dotori.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_books")
@Entity
public class MemberBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "review", length = 500, nullable = false)
    private String review;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private MemberBookStatus memberBookStatus;

    @Builder
    private MemberBook(
        Long memberId,
        Long bookId,
        Integer score,
        String review,
        MemberBookStatus memberBookStatus
    ) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.score = score;
        this.review = review;
        this.memberBookStatus = memberBookStatus;
    }
}
