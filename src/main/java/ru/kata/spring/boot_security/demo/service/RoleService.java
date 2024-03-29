package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.Role;
import java.util.List;
import java.util.Set;

public interface RoleService {
    void save(Role role);

    List<Role> getAllRoles();

    Role getOneRole(Long id);

    Set<Role> getRoles(Long[] roleId);
}
