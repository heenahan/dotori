package com.project.dotori.book.infrastructure.openfeign.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class AladinLookUpRequest {

    private String ttbKey;
    private String itemId;
    private String itemIdType;
    private String cover;
    private String output;
    private Integer version;

    public static AladinLookUpRequest of(
        String ttbKey,
        String itemId
    ) {
        return AladinLookUpRequest.builder()
            .ttbKey(ttbKey)
            .itemId(itemId)
            .itemIdType("ISBN13")
            .cover("Big")
            .output("JS")
            .version(20131101)
            .build();
    }
}
