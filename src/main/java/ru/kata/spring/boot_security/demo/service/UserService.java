package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService{
    List<User> getAllUsers();

    Optional<User> findById(Long id);

    void save(User user);

    void deleteUser(Long id);

    User findByUsername(String username);

    void updateUser(User user);
    User getOneUser(Long id);


}
