package com.example.cashcow_api.services.role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.role.RoleDTO;
import com.example.cashcow_api.models.ERole;

public interface IRole {
    
    ERole create(RoleDTO roleDTO);

    List<ERole> getAll();
    
    Optional<ERole> getById(Integer roleId);

    ERole getById(Integer roleId, Boolean handleException);

    Page<ERole> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ERole role);

    ERole update(Integer roleId, RoleDTO roleDTO);
}
