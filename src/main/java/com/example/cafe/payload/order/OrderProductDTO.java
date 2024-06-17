package com.example.cafe.payload.order;

import com.example.cafe.entity.OrderProduct;
import com.example.cafe.entity.Product;
import com.example.cafe.entity.enums.OrderEnum;
import com.example.cafe.payload.api.FileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderProductDTO {

    private List<UUID> images;

    private String title;

    private String unit;

    private Float price;

    private String currency;

    private Float discount;

    private OrderEnum status;

    private Integer quantity;

    public static OrderProductDTO map(OrderProduct orderProduct){
        Product product = orderProduct.getProduct();

        return OrderProductDTO
                .builder()
                .images(FileDTO.mapFile(product.getImages()))
                .title(product.getTitle())
                .unit(product.getUnit())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .discount(product.getDiscount())
                .status(orderProduct.getStatus())
                .quantity(orderProduct.getQuantity())
                .build();
    }

    public static List<OrderProductDTO> map(List<OrderProduct> orderProducts){
        return orderProducts
                .stream()
                .map(OrderProductDTO::map)
                .collect(Collectors.toList());
    }
}
