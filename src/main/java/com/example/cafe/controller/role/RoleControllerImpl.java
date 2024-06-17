package com.example.cafe.controller.role;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.role.RoleAddEditDTO;
import com.example.cafe.payload.role.RoleDTO;
import com.example.cafe.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    @Override
    public ApiResult<RoleDTO> add(RoleAddEditDTO roleAddDTO) {
        return roleService.add(roleAddDTO);
    }

    @Override
    public ApiResult<List<RoleDTO>> list() {
        return roleService.list();
    }

    @Override
    public ApiResult<RoleDTO> getOne(UUID id) {
        return roleService.getOne(id);
    }

    @Override
    public ApiResult<RoleDTO> update(RoleAddEditDTO roleAddDTO, UUID id) {
        return roleService.update(roleAddDTO, id);
    }

    @Override
    public ApiResult<RoleDTO> getForAdd() {
        return roleService.getForAdd();
    }

    @Override
    public ApiResult<?> deleteRole(UUID id, UUID insteadOfRoleId) {
        return roleService.deleteRole(id, insteadOfRoleId);
    }
}
