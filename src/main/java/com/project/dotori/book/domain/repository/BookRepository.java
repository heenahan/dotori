package com.project.dotori.book.domain.repository;

import com.project.dotori.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
