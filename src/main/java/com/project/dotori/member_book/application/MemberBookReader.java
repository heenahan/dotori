package com.project.dotori.member_book.application;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.MemberBookQueryRepository;
import com.project.dotori.member_book.domain.repository.MemberBookRepository;
import com.project.dotori.member_book.domain.repository.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MemberBookReader {

    private static final String NOT_FOUND = "도서 기록을 찾을 수 없습니다. value = %d";
    private final MemberBookRepository memberBookRepository;
    private final MemberBookQueryRepository memberBookQueryRepository;

    public MemberBook findOne(
        Long memberBookId
    ) {
        return memberBookRepository.findById(memberBookId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND.formatted(memberBookId)));
    }

    public MemberBookDetailQueryResponse findDetailOne(
        Long memberBookId
    ) {
        return memberBookRepository.findMemberBookDetailById(memberBookId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND.formatted(memberBookId)));
    }

    public Slice<MemberBookQueryResponse> findAllByStatus(
        Long memberId,
        MemberBookStatus memberBookStatus,
        Pageable pageable
    ) {
        return memberBookQueryRepository.findByMemberIdAndMemberBookStatus(
            memberId,
            memberBookStatus,
            pageable
        );
    }

    public StatisticsYearQueryResponse findStatisticsYearResponse(
        Long memberId,
        Integer year
    ) {
        return memberBookRepository.calculateTotalBookAndPage(memberId, year);
    }

    public List<StatisticsMonthQueryResponse> findStatisticsMonthResponses(
        Long memberId,
        Integer year
    ) {
        return memberBookRepository.calculateMonthlyCount(memberId, year);
    }

    public StatisticsStarAverageQueryResponse findStatisticsStarAverageResponse(
        Long memberId,
        Integer year
    ) {
        return memberBookRepository.calculateStarAverage(memberId, year);
    }

    public List<StatisticsStarQueryResponse> findStatisticsStarRatioResponses(
        Long memberId,
        Integer year
    ) {
        return memberBookRepository.calculateStarRatio(memberId, year);
    }

    public List<StatisticsCategoryQueryResponse> findStatisticsCategoryResponses(
        Long memberId,
        Integer year
    ) {
        return memberBookRepository.calculateTopCategory(memberId, year);
    }
}
