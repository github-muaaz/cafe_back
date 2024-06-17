package com.example.cafe.payload.order;

import com.example.cafe.entity.Order;
import com.example.cafe.entity.enums.OrderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private UUID id;

    private String table;

    private OrderEnum status;

    private List<OrderProductDTO> orderProducts;

    private LocalDateTime time;

    public static OrderDTO map(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .table(order.getTable().getName())
                .status(order.getStatus())
                .orderProducts(OrderProductDTO.map(order.getOrderProducts()))
                .time(order.getUpdatedAt())
                .build();
    }

    public static List<OrderDTO> map(List<Order> orders) {
        return orders
                .stream()
                .map(OrderDTO::map)
                .collect(Collectors.toList());
    }
}
