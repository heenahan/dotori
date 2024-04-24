package com.project.dotori.member_book.presentation;

import com.project.dotori.global.response.ApiResponse;
import com.project.dotori.member_book.application.MemberBookService;
import com.project.dotori.member_book.application.response.MemberBookResponse;
import com.project.dotori.member_book.presentation.request.MemberBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/member-books")
@RestController
public class MemberBookController {

    private static final String CREATE_URL = "/api/v1/member-books/%d";

    private final MemberBookService memberBookService;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberBookResponse>> createMemberBook(
        @RequestBody MemberBookRequest request
    ) {
        var memberId = 1L;
        var serviceRequest = request.toService();
        var response = memberBookService.createMemberBookRead(memberId, serviceRequest);

        var uri = URI.create(CREATE_URL.formatted(response.memberBookId()));

        return ResponseEntity.created(uri)
            .body(ApiResponse.created(response));
    }
}
