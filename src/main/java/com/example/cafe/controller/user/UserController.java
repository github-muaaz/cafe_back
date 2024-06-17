package com.example.cafe.controller.user;

import com.example.cafe.entity.User;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.user.UserAddEditDTO;
import com.example.cafe.payload.user.UserDTO;
import com.example.cafe.payload.user.UserMeDTO;
import com.example.cafe.security.CurrentUser;
import com.example.cafe.utils.RestConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(path = UserController.USER_CONTROLLER_BASE_PATH)
public interface UserController {
    String USER_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "user";
    String USER_ME_PATH = "/me";
    String LOG_OUT_PATH = "/logout";
    String GET_USER_PATH = "/{id}";
    String GET_USER_LIST_PATH = "/list/{page}/{size}";

    @ApiOperation(value = "get logged in user by token path")
    @GetMapping(value = USER_ME_PATH)
    ApiResult<UserMeDTO> getUserMe(@CurrentUser User user);

    @ApiOperation(value = "log out path")
    @GetMapping(LOG_OUT_PATH)
    ApiResult<Boolean> logout(@CurrentUser User user);

    @ApiOperation(value = "user add path")
    @PostMapping
    ApiResult<UserDTO> add(@Valid @RequestBody UserAddEditDTO addEditDTO);

    @ApiOperation(value = "get one user path")
    @GetMapping(GET_USER_PATH)
    ApiResult<UserDTO> get(@PathVariable UUID id);

    @ApiOperation(value = "get list of users path")
    @GetMapping(GET_USER_LIST_PATH)
    ApiResult<?> getAll(@PathVariable Integer page, @PathVariable Integer size);
}
