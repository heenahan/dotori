package com.project.dotori.member_book.presentation;

import com.project.dotori.global.response.ApiResponse;
import com.project.dotori.member_book.application.MemberBookService;
import com.project.dotori.member_book.application.response.MemberBookCreateResponse;
import com.project.dotori.member_book.presentation.request.MemberBookCreateRequest;
import com.project.dotori.member_book.presentation.request.MemberBookUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/member-books")
@RestController
public class MemberBookController {

    private static final String CREATE_URL = "/api/v1/member-books/%d";

    private final MemberBookService memberBookService;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberBookCreateResponse>> createMemberBook(
        @RequestBody MemberBookCreateRequest request
    ) {
        var memberId = 1L;
        var serviceRequest = request.toService();
        var response = memberBookService.createMemberBook(memberId, serviceRequest);

        var uri = URI.create(CREATE_URL.formatted(response.memberBookId()));

        return ResponseEntity.created(uri)
            .body(ApiResponse.created(response));
    }

    @PatchMapping("/{memberBookId}")
    public ResponseEntity<ApiResponse<Void>> updateMemberBook(
        @PathVariable("memberBookId") Long memberBookId,
        @RequestBody MemberBookUpdateRequest request
    ) {
        var memberId = 1L;
        var serviceRequest = request.toService(memberBookId);
        memberBookService.updateMemberBook(memberId, serviceRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.noContent());
    }
}
