package com.swd391.backend.dao;

import com.swd391.backend.entity.Role;
import com.swd391.backend.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@Tag(name = "Role", description = "Role management APIs")
@RepositoryRestResource(path = "role")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByRoleName(String name);
}
