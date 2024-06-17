package com.example.cafe.payload.auth;

import com.example.cafe.entity.enums.PageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

    private final String tokenType = "Bearer ";

    private PageEnum defaultPage;
}
