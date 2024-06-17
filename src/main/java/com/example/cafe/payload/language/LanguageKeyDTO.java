package com.example.cafe.payload.language;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageKeyDTO {
    
    private UUID id;

    @NotBlank
    private String key;

    private boolean deleted;
}
