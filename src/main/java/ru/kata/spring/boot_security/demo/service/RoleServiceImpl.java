package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getOneRole(Long id) {
        Optional<Role> foundOneRole = roleRepository.findById(id);
        return foundOneRole.orElse(null);
    }

    @Override
    public Set<Role> getRoles(Long[] roleId) {
        Set<Role> roleResult = new HashSet<>();


        if (roleId == null) {
            roleResult.add(roleRepository.findAll().get(0));
        } else {
            for (long i : roleId) {
                List<Role> roles = roleRepository.findAll();
                roleResult.add(roles.stream().filter(r -> r.getId() == i).findAny().orElse(null));
            }
        }

        return roleResult;
    }
}

