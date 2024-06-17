package com.example.cafe.payload.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchDTO {

    private UUID id;

    private String name;

    private String field;
}
