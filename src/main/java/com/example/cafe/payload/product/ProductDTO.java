package com.example.cafe.payload.product;

import com.example.cafe.entity.Category;
import com.example.cafe.entity.Product;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.FileDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private UUID id;

    private String title;

    private Float price;

    private String description;

    private String unit;

    private String currency;

    private List<CategoryDTO> categories;

    private UniStatusEnum status;

    private List<UUID> images;

    private Set<String> ingredients;

    private Float discount;


    public static ProductDTO map(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .unit(product.getUnit())
                .currency(product.getCurrency())
                .categories(CategoryDTO.mapToIdName(product.getCategories()))
                .status(product.getStatus())
                .images(FileDTO.mapFile(product.getImages()))
                .ingredients(product.getIngredient())
                .discount(product.getDiscount())
                .build();
    }

    public static List<ProductDTO> map(List<Product> product) {
        return product.stream()
                .map(ProductDTO::map)
                .collect(Collectors.toList());
    }

    private static List<String> getCategories(List<Category> categories) {
        return categories
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
