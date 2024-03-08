package com.project.dotori.user;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Builder
    private Member(
        String nickname
    ) {
        validNicknameLength(nickname);
        this.nickname = nickname;
    }

    private void validNicknameLength(
        String nickname
    ) {
        if (!StringUtils.hasText(nickname) && nickname.length() > 10) {
            throw new BusinessException(ErrorCode.INVALID_NICKNAME);
        }
    }
}
