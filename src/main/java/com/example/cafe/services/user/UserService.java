package com.example.cafe.services.user;

import com.example.cafe.entity.User;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.user.UserAddEditDTO;
import com.example.cafe.payload.user.UserDTO;
import com.example.cafe.payload.user.UserMeDTO;

import java.util.UUID;

public interface UserService {

    ApiResult<UserMeDTO> getUserMe(User user);

    ApiResult<Boolean> logout(User user);

    ApiResult<UserDTO> add(UserAddEditDTO addEditDTO);

    ApiResult<UserDTO> get(UUID id);

    ApiResult<?> getAll(Integer page, Integer size);
}
