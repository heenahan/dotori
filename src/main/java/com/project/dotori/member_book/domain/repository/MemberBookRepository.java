package com.project.dotori.member_book.domain.repository;

import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.repository.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query("""
        select 
            new com.project.dotori.member_book.domain.repository.response.StatisticsYearQueryResponse(
                count(mb.bookId), 
                nullif(sum(mb.bookReview.page), 0)
            )
        from MemberBook mb
        where mb.memberId = :memberId and year (mb.readingDate.endDate) = :year and  mb.memberBookStatus = 'READ'
    """)
    StatisticsYearQueryResponse calculateTotalBookAndPage(
        @Param("memberId") Long memberId,
        @Param("year") Integer year
    );

    @Query("""
        select 
            new com.project.dotori.member_book.domain.repository.response.StatisticsStarAverageQueryResponse(
                nullif(round(sum(mb.bookReview.star) / count(mb.bookReview.star), 1), 0) 
            )
        from MemberBook mb
        where mb.memberId = :memberId and year (mb.readingDate.endDate) = :year and mb.memberBookStatus = 'READ'
    """)
    StatisticsStarAverageQueryResponse calculateStarAverage(
        @Param("memberId") Long memberId,
        @Param("year") Integer year
    );

    @Query(value = """
        WITH stars AS (
        	SELECT 0.0 AS star
        	UNION SELECT 0.5
        	UNION SELECT 1.0
        	UNION SELECT 1.5
        	UNION SELECT 2.0
        	UNION SELECT 2.5
        	UNION SELECT 3.0
        	UNION SELECT 3.5
        	UNION SELECT 4.0
        	UNION SELECT 4.5
        	UNION SELECT 5.0
        )
        
        select
        	s.star as star,
        	round(count(mb.star) / sum(count(mb.star)) over() * 100, 0) as percentage
        from stars s
        left join member_books mb on mb.star = s.star 
            and year(mb.end_date) = :year
            and mb.member_id = :memberId 
            and mb.status = 'READ'
        group by s.star
    """, nativeQuery = true)
    List<StatisticsStarQueryResponse> calculateStarRatio(
        @Param("memberId") Long memberId,
        @Param("year") Integer year
    );

    @Query(value = """
        with months as (
            select 1 as months
            union select 2
            union select 3
            union select 4
            union select 5
            union select 6
            union select 7
            union select 8
            union select 9
            union select 10
            union select 11
            union select 12
        ), monthly_count_books as (
        	select
        		month(mb.end_date) as months,
                count(mb.book_id) as cnt
        	from member_books mb
        	where mb.member_id = :memberId and year(mb.end_date) = :year and mb.status = 'READ'
            group by month(mb.end_date)
        )
        
        select
            m.months as months,
            ifnull(mcb.cnt, 0) as cnt
        from
            months m
        left join monthly_count_books mcb on m.months = mcb.months
    """, nativeQuery = true)
    List<StatisticsMonthQueryResponse> calculateMonthlyCount(
        @Param("memberId") Long memberId,
        @Param("year") Integer year
    );
}
