package com.example.cafe.controller.order;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.order.OrderAddDTO;
import com.example.cafe.payload.order.OrderDTO;
import com.example.cafe.payload.order.TableOrderDTO;
import com.example.cafe.repository.OrderRepository;
import com.example.cafe.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController{

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Override
    public ApiResult<OrderDTO> add(OrderAddDTO orderDTO) {
        return orderService.add(orderDTO);
    }

    @Override
    public ApiResult<OrderDTO> get(UUID id) {
        return null;
    }

    @Override
    public ApiResult<List<OrderDTO>> getAll(Integer page, Integer size) {
        return orderService.getAllForAdmin(page, size);
    }

    @Override
    public ApiResult<List<TableOrderDTO>> getAll(UUID tableId) {
        return orderService.getAll(tableId);
    }

    @Override
    public ApiResult<CategoryDTO> edit(UUID id, CategoryAddEditDTO category) {
        return null;
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return null;
    }
}
