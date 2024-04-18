package com.project.dotori.book.domain;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PublishInfo {

    private static final String INVALID_PUBLISHER = "출판사는 50자 이하여야 합니다. length = %d";
    private static final String AFTER_PUBLISH_DATE = "출판 일자는 null이거나 오늘보다 미래여서는 안됩니다. value = %s";
    private static final String ZERO_PUBLISH_DATE = "출판 일자에 0이 들어가서는 안됩니다. value = %s";

    @Column(name = "publisher", length = 50, nullable = false)
    private String publisher;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Builder
    private PublishInfo(
        String publisher,
        LocalDate publishDate
    ) {
        validPublisher(publisher);
        validPublishDate(publishDate);

        this.publisher = publisher;
        this.publishDate = publishDate;
    }

    private void validPublisher(
        String publisher
    ) {
        if (StringUtils.isBlank(publisher) || publisher.length() > 13) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_PUBLISHER.formatted(StringUtils.length(publisher)));
        }
    }

    private void validPublishDate(
        LocalDate publishDate
    ) {
        var now = LocalDate.now();
        if (Objects.isNull(publishDate) || publishDate.isAfter(now)) {
            throw new BusinessException(ErrorCode.INVALID_DATE, AFTER_PUBLISH_DATE.formatted(publishDate));
        }
        if (publishDate.getYear() == 0) {
            throw new BusinessException(ErrorCode.INVALID_DATE, ZERO_PUBLISH_DATE.formatted(publishDate));
        }
    }
}
