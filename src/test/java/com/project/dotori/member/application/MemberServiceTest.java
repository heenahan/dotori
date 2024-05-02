package com.project.dotori.member.application;

import com.project.dotori.member.application.request.MemberCreateRequest;
import com.project.dotori.member.domain.Member;
import com.project.dotori.member.domain.Role;
import com.project.dotori.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버를 저장한다.")
    @Test
    void saveMember() {
        // given
        var request = createRequest();

        // when
        var memberId = memberService.createMember(request);

        // then
        var memberOptional = memberRepository.findById(memberId);
        assertThat(memberOptional).isNotEmpty();
        var member = memberOptional.get();
        assertThat(member).extracting(
            "id",
            "socialId",
            "email.email",
            "nickname.nickname",
            "role"
        ).containsExactly(memberId, request.socialId(), request.email(), request.name(), request.role());
    }

    @DisplayName("이미 존재하는 멤버라면 새로 저장하지 않고 기존 멤버를 반환한다.")
    @Test
    void existMember() {
        // given
        var member = createMember();
        var request = createRequest(member);
        memberRepository.save(member);

        // when
        var memberId = memberService.createMember(request);

        // then
        assertThat(memberId).isEqualTo(member.getId());
    }

    private Member createMember() {
        return Member.builder()
            .socialId("1234")
            .nickname("nickname")
            .email("abc@abc.com")
            .role(Role.GOOGLE)
            .build();
    }

    private MemberCreateRequest createRequest() {
        return MemberCreateRequest.builder()
            .socialId("1234")
            .email("abc@abc.com")
            .name("name")
            .role(Role.GOOGLE)
            .build();
    }

    private MemberCreateRequest createRequest(
        Member member
    ) {
        return MemberCreateRequest.builder()
            .socialId(member.getSocialId())
            .email(member.getEmail().getEmail())
            .name(member.getNickname().getNickname())
            .role(member.getRole())
            .build();
    }
}