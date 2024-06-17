package com.example.cafe.services.user;

import com.example.cafe.entity.Role;
import com.example.cafe.entity.User;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.user.UserAddEditDTO;
import com.example.cafe.payload.user.UserDTO;
import com.example.cafe.payload.user.UserMeDTO;
import com.example.cafe.repository.RoleRepository;
import com.example.cafe.repository.UserRepository;
import com.example.cafe.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public ApiResult<UserMeDTO> getUserMe(User user) {
        checkUserExist(user);

        UserMeDTO userDTO = UserMeDTO.mapUserMeDTO(user);

        return ApiResult.successResponse(userDTO);
    }

    @Override
    public ApiResult<Boolean> logout(User user) {
        checkUserExist(user);

        return null;
    }

    @Override
    public ApiResult<UserDTO> add(UserAddEditDTO addEditDTO) {

        if (userRepository.existsByEmail(addEditDTO.getEmail()))
            throw RestException
                    .restThrow(MessageConstants.ALREADY_EXISTS, HttpStatus.CONFLICT);

        if (Objects.isNull(addEditDTO.getPassword()))
            throw RestException
                    .restThrow(MessageConstants.PASSWORD_SHOULD_NOT_BLANK, HttpStatus.BAD_REQUEST);

        if (Objects.isNull(addEditDTO.getFirstname()))
            throw RestException
                    .restThrow(MessageConstants.FIRSTNAME_SHOULD_NOT_BLANK, HttpStatus.BAD_REQUEST);

        Role role = roleRepository.findById(addEditDTO.getRoleId())
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND));

        User user = User.builder()
                .password(passwordEncoder.encode(addEditDTO.getPassword().trim()))
                .firstname(addEditDTO.getFirstname().trim())
                .lastname(addEditDTO.getLastname().trim())
                .email(addEditDTO.getEmail())
                .enabled(true)
                .role(role)
                .build();

        user = userRepository.save(user);

        return ApiResult.successResponse(UserDTO.mapUserDTO(user), "SUCCESSFULLY_TOKEN_GENERATED");
    }

    @Override
    public ApiResult<UserDTO> get(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(UserDTO.mapUserDTO(user));
    }

    @Override
    public ApiResult<?> getAll(Integer page, Integer size) {

        Page<User> pagedUsers = userRepository.findAll(PageRequest.of(page, size));

        return ApiResult.successResponse(
                PageData
                        .generate(
                                UserDTO.mapUserDTO(pagedUsers.getContent()),
                                pagedUsers.getSize(),
                                pagedUsers.getNumber(),
                                pagedUsers.getTotalPages(),
                                pagedUsers.getTotalElements()
                        ));
    }

    private void checkUserExist(User user) {
        if (Objects.isNull(user))
            throw RestException
                    .restThrow("User not found", HttpStatus.UNAUTHORIZED);
    }
}
