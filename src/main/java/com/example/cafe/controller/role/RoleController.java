package com.example.cafe.controller.role;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.role.RoleAddEditDTO;
import com.example.cafe.payload.role.RoleDTO;
import com.example.cafe.utils.RestConstants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(value = RoleController.BASE_PATH)
public interface RoleController {

    String BASE_PATH = RestConstants.BASE_PATH + "role";
    String GET_PAGES_FOR_ADD = "page";

    @PostMapping
    ApiResult<RoleDTO> add(@Valid @RequestBody RoleAddEditDTO roleAddDTO);

    @GetMapping
    ApiResult<List<RoleDTO>> list();

    @GetMapping("/{id}")
    ApiResult<RoleDTO> getOne(@PathVariable UUID id);

    @PutMapping("/{id}")
    ApiResult<RoleDTO> update(@Valid @RequestBody RoleAddEditDTO roleAddDTO,
                              @PathVariable UUID id);

    @GetMapping(value = GET_PAGES_FOR_ADD)
    ApiResult<RoleDTO> getForAdd();

    /**
     * ROLE NI O'CHIRISH. AGAR ROLE USGA BIRIKTIRILGAN BO'LSA,
     * SHU REQUESTGA RESPONSE SIFATIDA TANLSHI KERAK BO'LGAN ROLE LAR LISTI TANLAB BERILADI,
     * KEYINGI REQUESTDA O'CHIIRILAYOTGAN ROLE VA USHBU ROLE ISHLATILGAN USERLARGA QAYSI ROLENI
     * O'RNATMOQCHI BO'LSAK UNI ID SI KELADI
     *
     * @param id              @PathVariable Long
     * @param insteadOfRoleId @PathVariable(required = false) Long
     * @return {@link ApiResult}<?>
     */
    @DeleteMapping(path = {"/{id}", "{id}/{insteadOfRoleId}"})
    ApiResult<?> deleteRole(@PathVariable UUID id,
                            @PathVariable UUID insteadOfRoleId);

}
