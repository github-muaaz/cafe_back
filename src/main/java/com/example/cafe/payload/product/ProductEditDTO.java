package com.example.cafe.payload.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ProductEditDTO {

    private UUID id;

    private String title;

    private Float price;

    private String description;

    private String unit;

    private String currency;

    private List<UUID> categoryIds;

    private Set<UUID> oldFiles;

    private List<MultipartFile> images;

    private Set<String> ingredients;

    private Float discount;
}
