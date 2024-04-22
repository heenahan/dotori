package com.project.dotori.member_book.presentation;

import com.project.dotori.global.response.ApiResponse;
import com.project.dotori.member_book.application.MemberBookService;
import com.project.dotori.member_book.presentation.request.MemberBookReadRequest;
import com.project.dotori.member_book.presentation.response.MemberBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/member-books")
@RestController
public class MemberBookController {

    private static final String MEMBER_BOOK_URI = "/api/v1/member-books/%d";
    private final MemberBookService memberBookService;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberBookResponse>> createMemberBookRead(
        @RequestParam("memberId") Long memberId,
        @RequestBody MemberBookReadRequest request
    ) {
        var memberBookReadServiceRequest = request.toService();
        var response = memberBookService.createMemberBookRead(memberId, memberBookReadServiceRequest);
        var location = URI.create(MEMBER_BOOK_URI.formatted(response.memberBookId()));

        return ResponseEntity.created(location)
            .body(ApiResponse.created(response));
    }
}
