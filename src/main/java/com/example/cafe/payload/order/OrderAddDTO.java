package com.example.cafe.payload.order;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderAddDTO {

    private UUID tableId;

    private List<OrderProductAddDTO> orderProducts;
}
