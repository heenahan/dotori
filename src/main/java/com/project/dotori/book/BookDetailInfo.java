package com.project.dotori.book;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BookDetailInfo {

    @Lob
    @Column(name = "toc", nullable = true)
    private String toc;

    @Lob
    @Column(name = "story", nullable = true)
    private String story;

    @Builder
    private BookDetailInfo(
        String toc,
        String story
    ) {
        this.toc = toc;
        this.story = story;
    }
}
