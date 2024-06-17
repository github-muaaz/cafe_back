package com.example.cafe.payload.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageData<T> {
    private T content;
    private Integer size;
    private Integer pageNum;
    private Integer totalPages;
    private Long totalElements;
    private String customField;

    public static <T> PageData<T> generate(T content, Integer size, Integer pageNum, Integer totalPages, Long totalElements, String customField){
        return new PageData<>(content, size, pageNum, totalPages, totalElements, customField);
    }

    public static <T> PageData<T> generate(T content, Integer size, Integer pageNum, Integer totalPages, Long totalElements){
        return PageData.generate(content, size, pageNum, totalPages, totalElements, null);
    }
}
