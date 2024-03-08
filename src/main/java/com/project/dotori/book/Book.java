package com.project.dotori.book;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "books")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn", length = 13, nullable = false)
    private String isbn;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "publisher", length = 50, nullable = false)
    private String publisher;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "cover_path", length = 100, nullable = false)
    private String coverPath;

    @Column(name = "page", nullable = false)
    private Integer page;

    @Lob
    @Column(name = "toc", nullable = true)
    private String toc;

    @Lob
    @Column(name = "story", nullable = true)
    private String story;

    @Builder
    private Book (
        String isbn,
        Long categoryId,
        String title,
        String author,
        String description,
        Integer price,
        String publisher,
        LocalDate publishDate,
        String coverPath,
        Integer page
    ) {
        this.isbn = isbn;
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.coverPath = coverPath;
        this.page = page;
    }
}
