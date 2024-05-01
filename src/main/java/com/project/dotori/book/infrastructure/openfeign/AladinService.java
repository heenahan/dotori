package com.project.dotori.book.infrastructure.openfeign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dotori.book.application.BookApiService;
import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import com.project.dotori.book.infrastructure.openfeign.request.AladinLookUpRequest;
import com.project.dotori.book.infrastructure.openfeign.request.AladinSearchRequest;
import com.project.dotori.book.infrastructure.openfeign.response.AladinLookUpResponse;
import com.project.dotori.book.infrastructure.openfeign.response.AladinSearchResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AladinService implements BookApiService {

    private final AladinOpenFeign aladinOpenFeign;
    private final AladinProperties aladinProperties;
    private final ObjectMapper objectMapper;

    @Override
    public List<BookSearchResponse> searchBooks(
        String query,
        Pageable pageable
    ) {
        try {
            var searchURI = aladinProperties.createSearchURI();
            var ttbKey = aladinProperties.getTtbkey();
            var request = AladinSearchRequest.of(ttbKey, query, pageable);

            var response = aladinOpenFeign.searchBooks(searchURI, request);

            var aladinSearchResponses = objectMapper.readValue(response, AladinSearchResponses.class);

            return aladinSearchResponses.toBookSearchResponses();
        } catch (IOException e) {
            log.warn("json을 객체로 변환하는 과정에서 예외가 발생했습니다. exception : ", e);
            throw new ServerErrorException("서버에서 예기치못한 문제가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<BookDetailResponse> findBookDetail(
        String isbn13
    ) {
        try {
            var lookUpURI = aladinProperties.createLookUpURI();
            var ttbKey = aladinProperties.getTtbkey();
            var request = AladinLookUpRequest.of(ttbKey, isbn13);

            var response = aladinOpenFeign.findBookDetail(lookUpURI, request);

            var jsonNode = objectMapper.readTree(response).get("item");
            if (jsonNode == null) {
                return Optional.empty();
            }

            var elements = jsonNode.elements();
            var aladinLookUpResponse = objectMapper.treeToValue(elements.next(), AladinLookUpResponse.class);

            return Optional.of(aladinLookUpResponse.toBookDetailResponse());
        } catch (IOException e) {
            log.warn("json을 객체로 변환하는 과정에서 예외가 발생했습니다. exception : ", e);
            throw new ServerErrorException("서버에서 예기치못한 문제가 발생했습니다.", e);
        }
    }
}
