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

    // Суть этого метода заключается в том, чтобы получить список всех ролей, хранящихся в базе данных. Этот список ролей может быть использован,для отображения доступных ролей при создании или редактировании пользователя.
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Optional<Role> foundOneRole = roleRepository.findById(id); - Здесь используется метод findById репозитория, предоставляемый Spring Data JPA. Этот метод позволяет найти роль по её id.
    // return foundOneRole.orElse(null); - Эта строка возвращает найденную роль, если она была найдена в БД, или null, если роль с указанным идентификатором не существует. Метод .orElse(null) используется для обработки ситуации, когда роль не найдена.
    @Override
    public Role getOneRole(Long id) {
        Optional<Role> foundOneRole = roleRepository.findById(id);
        return foundOneRole.orElse(null);
    }

    /**
     * // public Set<Role> getRoles(Long[] roleId) - Это объявление метода в интерфейсе сервиса. Он принимает массив id ролей и возвращает множество (Set) ролей.
     * // Set<Role> roleResult = new HashSet<>(); - Здесь создается пустой HashSet<>(), в который будут добавляться найденные роли.
     * // if (roleId == null) {...} else {...} - Эта конструкция проверяет, есть ли id ролей вообще. Если roleId равен null, то добавляется первая роль из списка ролей, полученных из репозитория.
     * // Если roleId не равен null, то выполняется цикл for (long i : roleId), который перебирает все переданные id ролей.
     * // List<Role> roles = roleRepository.findAll(); -  Здесь получается список всех ролей из репозитория.
     * // roleResult.add(roles.stream().filter(r -> r.getId() == i).findAny().orElse(null));: В этой строке используется Stream API для фильтрации списка ролей и нахождения роли с указанным id i.
     * // Если такая роль найдена, она добавляется в множество roleResult. Если не найдено ни одной роли с указанным id, будет добавлено значение null.
     * //
     * // На выходе из цикла возвращается HashSet<> roleResult, содержащий роли, соответствующие переданным id.
     **/
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

