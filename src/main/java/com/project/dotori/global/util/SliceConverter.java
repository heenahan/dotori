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

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
