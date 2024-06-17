package com.example.cafe.controller.table;

import com.example.cafe.entity.enums.TableEnum;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.table.TableAddEditDTO;
import com.example.cafe.payload.table.TableDTO;
import com.example.cafe.utils.RestConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = TableController.TABLE_CONTROLLER_BASE_PATH)
public interface TableController {
    String TABLE_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "table";
    String TABLE_ID_PATH = "/{id}";
    String GET_TABLE_LIST_PATH = "/list/{page}/{size}";
    String GET_ALL_TABLE_LIST_PATH = "/list/all";
    String EDIT_STATUS_PATH = "/status/{id}/{status}";

    @ApiOperation(value = "add new table")
    @PostMapping
    ApiResult<TableDTO> add(@RequestBody TableAddEditDTO table);

    @ApiOperation(value = "get one table")
    @GetMapping(value = TABLE_ID_PATH)
    ApiResult<TableDTO> get(@PathVariable UUID id);

    @ApiOperation(value = "get list of table")
    @GetMapping(value = GET_TABLE_LIST_PATH)
    ApiResult<List<TableDTO>> getAll(@PathVariable Integer page, @PathVariable Integer size);

    @ApiOperation(value = "get all list of table")
    @GetMapping(value = GET_ALL_TABLE_LIST_PATH)
    ApiResult<List<TableDTO>> getAll();

    @ApiOperation(value = "edit status of table")
    @PutMapping(value = EDIT_STATUS_PATH)
    ApiResult<TableDTO> editStatus(@PathVariable UUID id, @PathVariable TableEnum status);

    @ApiOperation(value = "edit table")
    @PutMapping(value = TABLE_ID_PATH)
    ApiResult<TableDTO> edit(@PathVariable UUID id, @RequestBody TableAddEditDTO table);

    @ApiOperation(value = "delete table")
    @DeleteMapping(value = TABLE_ID_PATH)
    ApiResult<?> delete(@PathVariable UUID id);
}
