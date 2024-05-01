package com.project.dotori.member_book.presentation;

import com.project.dotori.auth.MemberId;
import com.project.dotori.global.response.ApiResponse;
import com.project.dotori.member_book.application.MemberBookService;
import com.project.dotori.member_book.application.response.MemberBookCreateResponse;
import com.project.dotori.member_book.application.response.MemberBookDetailResponse;
import com.project.dotori.member_book.application.response.MemberBookResponse;
import com.project.dotori.member_book.presentation.request.MemberBookCreateRequest;
import com.project.dotori.member_book.presentation.request.MemberBookUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
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
        @MemberId Long memberId,
        @RequestBody @Valid MemberBookCreateRequest request
    ) {
        var serviceRequest = request.toService();
        var response = memberBookService.createMemberBook(memberId, serviceRequest);

        var uri = URI.create(CREATE_URL.formatted(response.memberBookId()));

        return ResponseEntity.created(uri)
            .body(ApiResponse.created(response));
    }

    @PatchMapping("/{memberBookId}")
    public ResponseEntity<ApiResponse<Void>> updateMemberBook(
        @MemberId Long memberId,
        @PathVariable("memberBookId") Long memberBookId,
        @RequestBody @Valid MemberBookUpdateRequest request
    ) {
        var serviceRequest = request.toService(memberBookId);
        memberBookService.updateMemberBook(memberId, serviceRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.noContent());
    }

    @DeleteMapping("/{memberBookId}")
    public ResponseEntity<ApiResponse<Void>> deleteMemberBook(
        @MemberId Long memberId,
        @PathVariable("memberBookId") Long memberBookId
    ) {
        memberBookService.deleteMemberBook(memberId, memberBookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.noContent());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Slice<MemberBookResponse>>> findAll(
        @MemberId Long memberId,
        @RequestParam("status") String status,
        @PageableDefault Pageable pageable
    ) {
        var responses = memberBookService.findAll(memberId, status, pageable);

        return ResponseEntity.ok(ApiResponse.ok(responses));
    }

    @GetMapping("/{memberBookId}")
    public ResponseEntity<ApiResponse<MemberBookDetailResponse>> findOne(
        @PathVariable("memberBookId") Long memberBookId
    ) {
        var response = memberBookService.findMemberBook(memberBookId);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
