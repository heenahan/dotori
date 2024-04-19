package com.project.dotori.book.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dotori.book.application.request.AladinLookUpRequest;
import com.project.dotori.book.application.request.AladinSearchRequest;
import com.project.dotori.book.application.response.AladinLookUpResponse;
import com.project.dotori.book.application.response.AladinSearchResponses;
import com.project.dotori.book.infrastructure.openfeign.AladinConfig;
import com.project.dotori.book.infrastructure.openfeign.AladinOpenFeign;
import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
@Service
public class AladinService {

    private static final String NOT_FOUND_BOOK = "책을 찾을 수 없습니다. 옳바르지 못한 isbn13입니다. value = %s";

    private final AladinOpenFeign aladinOpenFeign;
    private final AladinConfig aladinConfig;
    private final ObjectMapper objectMapper;

    public AladinSearchResponses searchBookByAladin(
        String query,
        Pageable pageable
    ) {
        try {
            var searchURI = aladinConfig.createSearchURI();
            var ttbKey = aladinConfig.getTtbkey();
            var request = AladinSearchRequest.of(ttbKey, query, pageable);

            var response = aladinOpenFeign.searchBooks(searchURI, request);

            return objectMapper.readValue(response, AladinSearchResponses.class);
        } catch (IOException e) {
            log.warn("json을 객체로 변환하는 과정에서 예외가 발생했습니다. exception : ", e);
            throw new ServerErrorException("서버에서 예기치못한 문제가 발생했습니다.", e);
        }
    }

    public AladinLookUpResponse findBookDetailByAladin(
        String isbn13
    ) {
        try {
            var lookUpURI = aladinConfig.createLookUpURI();
            var ttbKey = aladinConfig.getTtbkey();
            var request = AladinLookUpRequest.of(ttbKey, isbn13);

            var response = aladinOpenFeign.findBookDetail(lookUpURI, request);

            var arrayNode = objectMapper.readTree(response).get("item").elements();
            validBook(isbn13, arrayNode);

            return objectMapper.treeToValue(arrayNode.next(), AladinLookUpResponse.class);
        } catch (IOException e) {
            log.warn("json을 객체로 변환하는 과정에서 예외가 발생했습니다. exception : ", e);
            throw new ServerErrorException("서버에서 예기치못한 문제가 발생했습니다.", e);
        }
    }

    private void validBook(
        String isbn13,
        Iterator<JsonNode> arrayNode
    ) {
        if (!arrayNode.hasNext()) {
            throw new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND_BOOK.formatted(isbn13));
        }
    }
}
