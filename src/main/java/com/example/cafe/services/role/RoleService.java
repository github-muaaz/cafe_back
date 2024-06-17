package com.example.cafe.services.role;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.role.RoleAddEditDTO;
import com.example.cafe.payload.role.RoleDTO;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    ApiResult<RoleDTO> getOne(UUID id);

    ApiResult<List<RoleDTO>> list();

    ApiResult<RoleDTO> add(RoleAddEditDTO roleAddDTO);

    ApiResult<RoleDTO> update(RoleAddEditDTO roleAddDTO, UUID id);

    ApiResult<RoleDTO> getForAdd();

    ApiResult<?> deleteRole(UUID id, UUID insteadOfRoleId);
}
