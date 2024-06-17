package com.example.cafe.payload.user;

import com.example.cafe.entity.User;
import com.example.cafe.payload.role.RoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private RoleDTO role;

    private boolean status;

    public static UserDTO mapUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(RoleDTO.map(user.getRole()))
                .status(user.isEnabled())
                .build();
    }

    public static List<UserDTO> mapUserDTO(List<User> users) {
        return users
                .stream()
                .map(UserDTO::mapUserDTO)
                .collect(Collectors.toList());
    }
}

