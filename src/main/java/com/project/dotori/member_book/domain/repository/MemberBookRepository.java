package com.project.dotori.member_book.domain.repository;

import com.project.dotori.member_book.domain.MemberBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {
}
