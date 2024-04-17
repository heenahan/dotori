package com.project.dotori.book;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BookDetailInfo {

    private static final String INVALID_COVER_PATH = "표지 경로는 100자 이하여야 합니다. length = %d";
    private static final String INVALID_DESCRIPTION = "설명은 500자 이하여야 합니다. length = %d";

    @Column(name = "cover_path", length = 100, nullable = true)
    private String coverPath;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Builder
    private BookDetailInfo(
        String coverPath,
        String description
    ) {
        validLength(coverPath, description);
        this.coverPath = coverPath;
        this.description = description;
    }

    private void validLength(
        String coverPath,
        String description
    ) {
        if (StringUtils.isNoneBlank(coverPath) && coverPath.length() > 100) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_COVER_PATH.formatted(StringUtils.length(coverPath)));
        }
        if (StringUtils.isNoneBlank(description) && description.length() > 500) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_DESCRIPTION.formatted(StringUtils.length(description)));
        }
    }
}
