package com.example.cafe.payload.order;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderProductAddDTO {

    private UUID productId;

    private Integer quantity;
}
