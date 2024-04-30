package com.project.dotori.member.application;

import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberValidatorTest {

    @Autowired
    private MemberValidator memberValidator;

    @DisplayName("존재하지 않는 멤버라면 예외가 발생한다.")
    @Test
    void invalidMember() {
        // given
        var invalidMember = 1L;

        // when & then
        assertThatThrownBy(() -> memberValidator.validMember(invalidMember))
            .isInstanceOf(BusinessException.class);
    }
}