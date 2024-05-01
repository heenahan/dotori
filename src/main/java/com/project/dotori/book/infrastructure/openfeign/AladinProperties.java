package com.project.dotori.book.infrastructure.openfeign;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aladin")
public class AladinProperties {

    private final String ttbkey;
    private final String url;
    private final Uri uri;

    @Getter
    @RequiredArgsConstructor
    static class Uri {
        private final String search;
        private final String lookUp;
    }

    public URI createSearchURI() {
        return URI.create(url + uri.getSearch());
    }

    public URI createLookUpURI() {
        return URI.create(url + uri.getLookUp());
    }
}
