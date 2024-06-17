package com.example.cafe.services.order;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.order.OrderAddDTO;
import com.example.cafe.payload.order.OrderDTO;
import com.example.cafe.payload.order.TableOrderDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    ApiResult<OrderDTO> add(OrderAddDTO orderDTO);

    ApiResult<OrderDTO> get(UUID id);

    ApiResult<PageData<List<OrderDTO>>> getAll(Integer page, Integer size);

    ApiResult<List<OrderDTO>> getAllForAdmin(Integer page, Integer size);

    ApiResult<List<TableOrderDTO>> getAll(UUID tableId);

    ApiResult<CategoryDTO> edit(UUID id, CategoryAddEditDTO category);

    ApiResult<?> delete(UUID id);

}
