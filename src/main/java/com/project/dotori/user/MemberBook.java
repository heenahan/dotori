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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private MemberBookStatus memberBookStatus;

    @Embedded
    private MemberBookReview memberBookReview;

    @Builder
    private MemberBook(
        Long memberId,
        Long bookId,
        MemberBookStatus memberBookStatus
    ) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.memberBookStatus = memberBookStatus;
    }

    public void updateMemberBookReview(
        Integer score,
        String review
    ) {
        this.memberBookReview = MemberBookReview.builder()
            .score(score)
            .review(review)
            .build();
    }
}
