package com.project.dotori.member_book.domain.repository.response;

public interface StatisticsCategoryQueryResponse{
    Long getCategoryId();
    String getCategoryName();
    Long getCnt();
    Long getPercentage();
}
