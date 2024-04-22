package com.project.dotori.member_book.domain.repository;

import com.project.dotori.member_book.domain.MemberBookRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberBookRecordRepository extends JpaRepository<MemberBookRecord, Long> {

    Optional<MemberBookRecord> findByMemberBookId(Long memberBookId);
}
