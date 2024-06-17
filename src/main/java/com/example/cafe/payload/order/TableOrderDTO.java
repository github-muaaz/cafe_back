package com.example.cafe.payload.order;

import com.example.cafe.entity.Order;
import com.example.cafe.entity.OrderProduct;
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
public class TableOrderDTO {

    private UUID id;

    private String tableNo;

    private OrderEnum status;

    private String currency;

    private Float totalPrice;

    private Float discountPrice;

    private LocalDateTime time;

    private List<OrderProductDTO> orderProducts;

    public static TableOrderDTO map(Order order) {
        return TableOrderDTO.builder()
                .id(order.getId())
                .tableNo(order.getTable().getName())
                .status(order.getStatus())
                .currency(findCurrency(order.getOrderProducts()))
                .totalPrice(calculateTotalPrice(order.getOrderProducts()))
                .discountPrice(calculateDiscountPrice(order.getOrderProducts()))
                .time(order.getUpdatedAt())
                .orderProducts(OrderProductDTO.map(order.getOrderProducts()))
                .build();
    }

    private static String findCurrency(List<OrderProduct> orderProducts) {
        return orderProducts
                .stream()
                .map(orderProduct -> orderProduct.getProduct().getCurrency())
                .findAny()
                .orElse("원");
    }

    private static Float calculateDiscountPrice(List<OrderProduct> orderProducts) {
        float totalPrice = 0f;
        if (orderProducts != null) {
            for (OrderProduct orderProduct : orderProducts) {
                if (orderProduct.getProduct() != null && orderProduct.getQuantity() != null) {
                    Float productPrice = orderProduct.getProduct().getPrice();
                    Float discount = orderProduct.getProduct().getDiscount();
                    Integer quantity = orderProduct.getQuantity();

                    Float discountedPrice = discount != null ? productPrice * (1 - (discount / 100)) : productPrice;

                    totalPrice += discountedPrice * quantity;
                }
            }
        }
        return totalPrice;
    }

    private static Float calculateTotalPrice(List<OrderProduct> orderProducts) {
        float totalPrice = 0f;
        if (orderProducts != null) {
            for (OrderProduct orderProduct : orderProducts) {
                if (orderProduct.getProduct() != null && orderProduct.getQuantity() != null) {
                    Float productPrice = orderProduct.getProduct().getPrice();
                    Integer quantity = orderProduct.getQuantity();
                    totalPrice += productPrice * quantity;
                }
            }
        }
        return totalPrice;
    }

    public static List<TableOrderDTO> map(List<Order> orders) {
        return orders
                .stream()
                .map(TableOrderDTO::map)
                .collect(Collectors.toList());
    }
}
