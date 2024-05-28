package com.project.dotori.book.infrastructure.openfeign.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class AladinSearchRequest {

    private String ttbkey;
    private String query; // 검색어
    private String queryType;
    private Integer Start;
    private Integer maxResults;
    private String cover;
    private String output;
    private Integer version;

    public static AladinSearchRequest of (
        String ttbkey,
        String query,
        Pageable pageable
    ) {
        return AladinSearchRequest.builder()
            .ttbkey(ttbkey)
            .query(query)
            .queryType("Title")
            .Start(pageable.getPageNumber() + 1)
            .maxResults(pageable.getPageSize() + 1)
            .cover("Big")
            .output("JS")
            .version(20131101)
            .build();
    }
}
