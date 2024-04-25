package com.project.dotori.member_book.domain.repository;

import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.response.MemberBookQueryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MemberBookQueryRepository {

    Slice<MemberBookQueryResponse> findByMemberIdAndMemberBookStatus(
        Long memberId,
        MemberBookStatus memberBookStatus,
        Pageable pageable
    );
}
