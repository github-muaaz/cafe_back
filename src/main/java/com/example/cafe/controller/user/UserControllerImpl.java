package com.example.cafe.controller.user;

import com.example.cafe.entity.User;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.user.UserAddEditDTO;
import com.example.cafe.payload.user.UserDTO;
import com.example.cafe.payload.user.UserMeDTO;
import com.example.cafe.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ApiResult<UserMeDTO> getUserMe(User user) {
        return userService.getUserMe(user);
    }

    @Override
    public ApiResult<Boolean> logout(User user) {
        return userService.logout(user);
    }

    @Override
    public ApiResult<UserDTO> add(UserAddEditDTO addEditDTO) {
        return userService.add(addEditDTO);
    }

    @Override
    public ApiResult<UserDTO> get(UUID id) {
        return userService.get(id);
    }

    @Override
    public ApiResult<?> getAll(Integer page, Integer size) {
        return userService.getAll(page, size);
    }
}
