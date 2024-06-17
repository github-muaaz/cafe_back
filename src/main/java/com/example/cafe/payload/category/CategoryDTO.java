package com.example.cafe.payload.category;

import com.example.cafe.entity.Category;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    private UUID id;

    private String name;

    private CategoryDTO parent;

    private UniStatusEnum status;

    public static CategoryDTO map(Category category) {
        Category parentName = null;
        if (Objects.nonNull(category.getParent()))
            parentName = category.getParent();

        return CategoryDTO
                .builder()
                .parent(CategoryDTO.mapToIdName(parentName))
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .build();
    }

    public static List<CategoryDTO> map(List<Category> categories) {
       return categories.stream()
                .map(CategoryDTO::map)
                .collect(Collectors.toList());
    }

    public static CategoryDTO mapToIdName(Category category) {
        if (Objects.isNull(category))
            return null;
        return CategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryDTO> mapToIdName(List<Category> categories) {
        return categories.stream()
                .map(CategoryDTO::mapToIdName)
                .collect(Collectors.toList());
    }
}
