package com.example.cafe.payload.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ProductAddDTO {

    @NotBlank(message = "MUST_NOT_BE_BLANK_TITLE")
    private String title;

    @NotNull(message = "MUST_NOT_BE_NULL_PRICE")
    private Float price;

    private String description;

    @NotBlank(message = "MUST_NOT_BE_BLANK_UNIT")
    private String unit;

    @NotBlank(message = "MUST_NOT_BE_BLANK_CURRENCY")
    private String currency;

    private List<UUID> categoryIds;

    private List<MultipartFile> images;

    private Set<String> ingredients;

    private Float discount;
}
