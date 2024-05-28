package com.project.dotori.global.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public final class SliceConverter<T> {

    public static<T> Slice<T> toSlice(
        List<T> content,
        Pageable pageable
    ) {
        var hasNext = content.size() > pageable.getPageSize();
        // 마지막 원소 삭제
        if (hasNext) {
            content = content.subList(0, pageable.getPageSize() - 1);
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
