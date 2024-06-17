package com.example.cafe.payload.category;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
public class CategoryAddEditDTO {

    @NotBlank(message = "MUST_NOT_BE_BLANK_NAME")
    private String name;

    private UUID parentId;
}
