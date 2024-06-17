package com.example.cafe.services.role;

import com.example.cafe.entity.Role;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.role.AuthPageDTO;
import com.example.cafe.payload.role.RoleAddEditDTO;
import com.example.cafe.payload.role.RoleDTO;
import com.example.cafe.repository.RoleRepository;
import com.example.cafe.repository.UserRepository;
import com.example.cafe.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResult<RoleDTO> getOne(UUID id) {
        Role role = getRoleByIdOrThrow(id);

        return ApiResult.successResponse(RoleDTO.map(role));
    }

    @Override
    public ApiResult<List<RoleDTO>> list() {
        return ApiResult.successResponse(RoleDTO.map(roleRepository.findAll()));
    }

    @Override
    public ApiResult<RoleDTO> add(RoleAddEditDTO roleAddDTO) {
        if (roleRepository.existsByName(roleAddDTO.getName()))
            throw RestException
                    .restThrow(MessageConstants.ALREADY_EXISTS, HttpStatus.CONFLICT);

        Role role = roleRepository.save(Role.builder()
                .name(roleAddDTO.getName())
                .defaultPage(roleAddDTO.getDefaultPage())
                .permissions(roleAddDTO.getPermissions())
                .pages(roleAddDTO.getPages())
                .description(roleAddDTO.getDescription())
                .build());

        return ApiResult.successResponse(RoleDTO.map(role));
    }

    @Override
    public ApiResult<RoleDTO> update(RoleAddEditDTO roleAddDTO, UUID id) {
        Role role = getRoleByIdOrThrow(id);

        role.setName(roleAddDTO.getName());
        role.setDescription(roleAddDTO.getDescription());
        role.setDefaultPage(roleAddDTO.getDefaultPage());
        role.setPermissions(roleAddDTO.getPermissions());
        role.setPages(roleAddDTO.getPages());

        role = roleRepository.save(role);

        return ApiResult.successResponse(RoleDTO.map(role));
    }

    @Override
    public ApiResult<RoleDTO> getForAdd() {
        return ApiResult.successResponse(
                RoleDTO.builder()
                        .pages(AuthPageDTO.findAllPages())
                        .build());
    }

    @Override
    public ApiResult<?> deleteRole(UUID id, UUID insteadOfRoleId) {

        Role deletingRole = getRoleByIdOrThrow(id);

        if (Objects.equals(deletingRole.getName(), "ADMIN"))
            throw RestException
                    .restThrow(MessageConstants.ADMIN_ROLE_CAN_NOT_BE_DELETED, HttpStatus.BAD_REQUEST);

        if (!userRepository.existsByRoleId(id) || Objects.nonNull(insteadOfRoleId)) {
            if (Objects.nonNull(insteadOfRoleId)) {
                getRoleByIdOrThrow(insteadOfRoleId);
                userRepository.updateRole(id, insteadOfRoleId);
            }

            roleRepository.deleteById(id);

            return ApiResult.successResponse();
        }

        List<Role> suggestedRoles = roleRepository.findAllByIdIsNot(id);

        return ApiResult.successResponse(RoleDTO.map(suggestedRoles));
    }

    private Role getRoleByIdOrThrow(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
