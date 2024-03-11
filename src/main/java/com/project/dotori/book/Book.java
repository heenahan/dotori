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

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Embedded
    private BookBasicInfo bookBasicInfo;

    @Embedded
    private PublishInfo publishInfo;

    @Embedded
    private BookDetailInfo bookDetailInfo;

    @Builder
    private Book (
        Long categoryId,
        String isbn,
        String title,
        String author,
        String coverPath,
        Integer page,
        Integer price,
        String description,
        String publisher,
        LocalDate publishDate
    ) {
        this.categoryId = categoryId;
        this.bookBasicInfo = BookBasicInfo.builder()
            .isbn(isbn)
            .title(title)
            .author(author)
            .coverPath(coverPath)
            .description(description)
            .price(price)
            .page(page)
            .build();
        this.publishInfo = PublishInfo.builder()
            .publisher(publisher)
            .publishDate(publishDate)
            .build();
    }

    public void updateBookDetailInfo(
        String toc,
        String story
    ) {
        this.bookDetailInfo = BookDetailInfo.builder()
            .toc(toc)
            .story(story)
            .build();
    }
}
