package com.example.cafe.payload.language;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageDTO {
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    private UniStatusEnum status;
}
