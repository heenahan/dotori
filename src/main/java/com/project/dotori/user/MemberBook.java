package com.project.dotori.user;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "score", nullable = true)
    private Integer score;

    @Column(name = "review", length = 500, nullable = true)
    private String review;

    @Builder
    private MemberBook(
        Long memberId,
        Long bookId,
        MemberBookStatus memberBookStatus
    ) {
        validIsNotNull(memberId, bookId, memberBookStatus);
        this.memberId = memberId;
        this.bookId = bookId;
        this.memberBookStatus = memberBookStatus;
    }

    public void update(
        Integer score,
        String review
    ) {
        validScore(score);
        validReview(review);

        this.score = score;
        this.review = review;
    }

    private void validIsNotNull(
        Long memberId,
        Long bookId,
        MemberBookStatus memberBookStatus
    ) {
        List<String> filedNames = new ArrayList<>();
        if (Objects.isNull(memberId)) {
            filedNames.add("memberId");
        }
        if (Objects.isNull(bookId)) {
            filedNames.add("bookId");
        }
        if (Objects.isNull(memberBookStatus)) {
            filedNames.add("memberBookStatus");
            throw new BusinessException(ErrorCode.NOT_NULL, filedNames);
        }
    }

    private void validScore(
        Integer score
    ) {
        if (Objects.isNull(score) || score > 5 || score < 0) {
            throw new BusinessException(ErrorCode.INVALID_SCORE);
        }
    }

    private void validReview(
        String review
    ) {
        if (!StringUtils.hasText(review) || review.length() > 500) {
            throw new BusinessException(ErrorCode.INVALID_REVIEW);
        }
    }
}
