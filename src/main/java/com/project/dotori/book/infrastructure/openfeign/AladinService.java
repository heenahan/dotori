package com.project.dotori.book.infrastructure.openfeign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dotori.book.application.BookApiService;
import com.project.dotori.book.infrastructure.openfeign.request.AladinLookUpRequest;
import com.project.dotori.book.infrastructure.openfeign.request.AladinSearchRequest;
import com.project.dotori.book.infrastructure.openfeign.response.AladinLookUpResponse;
import com.project.dotori.book.infrastructure.openfeign.response.AladinSearchResponses;
import com.project.dotori.book.application.response.BookDetailResponse;
import com.project.dotori.book.application.response.BookSearchResponse;
import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class AladinService implements BookApiService {

    private static final String NOT_FOUND_BOOK = "책을 찾을 수 없습니다. 옳바르지 못한 isbn13입니다. value = %s";

    private final AladinOpenFeign aladinOpenFeign;
    private final AladinConfig aladinConfig;
    private final ObjectMapper objectMapper;

    @Override
    public List<BookSearchResponse> searchBooks(
        String query,
        Pageable pageable
    ) {
        try {
            var searchURI = aladinConfig.createSearchURI();
            var ttbKey = aladinConfig.getTtbkey();
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
    public BookDetailResponse findBookDetail(
        String isbn13
    ) {
        try {
            var lookUpURI = aladinConfig.createLookUpURI();
            var ttbKey = aladinConfig.getTtbkey();
            var request = AladinLookUpRequest.of(ttbKey, isbn13);

            var response = aladinOpenFeign.findBookDetail(lookUpURI, request);

            var jsonNode = objectMapper.readTree(response).get("item");
            validBook(isbn13, jsonNode);

            var elements = jsonNode.elements();
            var aladinLookUpResponse = objectMapper.treeToValue(elements.next(), AladinLookUpResponse.class);

            return aladinLookUpResponse.toBookDetailResponse();
        } catch (IOException e) {
            log.warn("json을 객체로 변환하는 과정에서 예외가 발생했습니다. exception : ", e);
            throw new ServerErrorException("서버에서 예기치못한 문제가 발생했습니다.", e);
        }
    }

    private void validBook(
        String isbn13,
        JsonNode jsonNode
    ) {
        if (Objects.isNull(jsonNode)) {
            throw new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND_BOOK.formatted(isbn13));
        }
    }
}
