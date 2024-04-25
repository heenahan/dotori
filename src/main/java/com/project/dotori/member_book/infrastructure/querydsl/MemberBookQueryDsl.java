package com.project.dotori.member_book.infrastructure.querydsl;

import com.project.dotori.global.util.SliceConverter;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.MemberBookQueryRepository;
import com.project.dotori.member_book.domain.repository.response.MemberBookQueryResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.project.dotori.book.domain.QBook.book;
import static com.project.dotori.member_book.domain.QMemberBook.memberBook;

@RequiredArgsConstructor
@Repository
public class MemberBookQueryDsl implements MemberBookQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<MemberBookQueryResponse> findByMemberIdAndMemberBookStatus(
        Long memberId,
        MemberBookStatus memberBookStatus,
        Pageable pageable
    ) {
        var memberBookQueryResponses = queryFactory.select(
                Projections.constructor(MemberBookQueryResponse.class,
                    book.bookDetailInfo.coverPath,
                    book.bookBasicInfo.title,
                    book.bookBasicInfo.author,
                    book.bookBasicInfo.page,
                    memberBook.id,
                    memberBook.memberBookStatus,
                    memberBook.readingDate.startDate,
                    memberBook.readingDate.endDate,
                    memberBook.bookReview.star,
                    memberBook.bookReview.page,
                    memberBook.bookReview.percentage,
                    memberBook.bookReview.bookLevel
                )
            ).from(book)
            .join(memberBook).on(book.isbn.eq(memberBook.bookId))
            .where(createBooleanBuilder(memberId, memberBookStatus))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1L)
            .fetch();

        return SliceConverter.toSlice(memberBookQueryResponses, pageable);
    }

    private BooleanBuilder createBooleanBuilder(
        Long memberId,
        MemberBookStatus memberBookStatus
    ) {
        return new BooleanBuilder().and(memberIdEq(memberId))
            .and(memberBookStatusEq(memberBookStatus));
    }

    private BooleanExpression memberIdEq(
        Long memberId
    ) {
        if (Objects.nonNull(memberId)) {
            return memberBook.memberId.eq(memberId);
        }
        return null;
    }

    private BooleanExpression memberBookStatusEq(
        MemberBookStatus memberBookStatus
    ) {
        if (Objects.nonNull(memberBookStatus)) {
            return memberBook.memberBookStatus.eq(memberBookStatus);
        }
        return null;
    }
}
