package com.project.dotori.book.infrastructure.openfeign;

import com.project.dotori.book.application.request.AladinLookUpRequest;
import com.project.dotori.book.application.request.AladinSearchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "AladinOpenFeign", url = "ALADIN_API_URI")
public interface AladinOpenFeign {

    @GetMapping
    String searchBooks(
        URI uri,
        @SpringQueryMap AladinSearchRequest aladinSearchRequest
    );

    @GetMapping
    String findBookDetail(
        URI uri,
        @SpringQueryMap AladinLookUpRequest aladinLookUpRequest
    );
}
