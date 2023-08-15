package ru.kata.spring.boot_security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class initApp {
    private final UserService userService;
    private final RoleService roleService;

    private final Role roleAdmin = new Role("ROLE_ADMIN");
    private final Role roleUser = new Role("ROLE_USER");

    public initApp(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    @Bean
    public void addUser() {
        roleService.save(roleAdmin);
        roleService.save(roleUser);

        ru.kata.spring.boot_security.demo.entities.User admin = new ru.kata.spring.boot_security.demo.entities.User("Admin", "Petrov", 26, "petrov@gmail.com",
                "admin", "$2a$12$Do58DAen9b6XutWWAW9gEu1yCNRmJVyFGqHu4fJpWlea0/nrT8RnW"); // password = 100
        ru.kata.spring.boot_security.demo.entities.User user = new ru.kata.spring.boot_security.demo.entities.User("User", "Ivanov", 31, "ivanov@gmail.com",
                "user", "$2a$12$Do58DAen9b6XutWWAW9gEu1yCNRmJVyFGqHu4fJpWlea0/nrT8RnW"); // password = 100

        admin.addRole(roleAdmin);
        user.addRole(roleUser);

        userService.save(admin);
        userService.save(user);

    }


}


