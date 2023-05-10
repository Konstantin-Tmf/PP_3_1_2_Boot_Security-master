package ru.kata.spring.boot_security.demo.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    Optional<User> findById(Long id);

    void saveUser(User user);

    void deleteById(Long id);

    User findByUsername(String username);

    void updateUser(User user);


}
