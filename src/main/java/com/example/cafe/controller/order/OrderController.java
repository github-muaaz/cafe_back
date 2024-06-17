package com.example.cafe.controller.order;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.order.OrderAddDTO;
import com.example.cafe.payload.order.OrderDTO;
import com.example.cafe.payload.order.TableOrderDTO;
import com.example.cafe.utils.RestConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = OrderController.ORDER_CONTROLLER_BASE_PATH)
public interface OrderController {
    String ORDER_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "order";
    String ORDER_ID_PATH = "/{id}";
    String GET_ORDER_LIST_PATH = "/list/{page}/{size}";
    String GET_ALL_ORDERS_BY_TABLE_PATH = "/table/{tableId}";

    @ApiOperation(value = "add new order")
    @PostMapping
    ApiResult<OrderDTO> add(@RequestBody OrderAddDTO orderDTO);

    @ApiOperation(value = "get one order")
    @GetMapping(value = ORDER_ID_PATH)
    ApiResult<OrderDTO> get(@PathVariable UUID id);

    @ApiOperation(value = "get list of orders")
    @GetMapping(value = GET_ORDER_LIST_PATH)
    ApiResult<List<OrderDTO>>getAll(@PathVariable Integer page, @PathVariable Integer size);

    @ApiOperation(value = "get list of order (by tableId)")
    @GetMapping(value = GET_ALL_ORDERS_BY_TABLE_PATH)
    ApiResult<List<TableOrderDTO>> getAll(@PathVariable UUID tableId);

    @ApiOperation(value = "edit category")
//    @PutMapping(value = CATEGORY_ID_PATH)
    ApiResult<CategoryDTO> edit(@PathVariable UUID id, @RequestBody CategoryAddEditDTO category);

    @ApiOperation(value = "delete category")
//    @DeleteMapping(value = CATEGORY_ID_PATH)
    ApiResult<?> delete(@PathVariable UUID id);
}
