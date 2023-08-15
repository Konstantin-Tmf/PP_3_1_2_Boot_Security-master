package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Метод loadUserByUsername является частью механизма аутентификации Spring Security.
    // Он загружает информацию о пользователе из базы данных и создает объект UserDetails,
    // который используется для проверки аутентичности и авторизации пользователя в системе.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Находим пользователя по имени пользователя (логину)
        User user = userRepository.findByUsername(username);
        // Если пользователь не найден, выбрасываем исключение UsernameNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        // Получаем роли, принадлежащие пользователю
        Collection<Role> roles = user.getRoles();

        // Создаем список для хранения GrantedAuthority (ролей и разрешений)
        List<GrantedAuthority> authorities = new ArrayList<>();
        // В цикле проходимся по списку ролей пользователя, и каждая роль добавляется в список разрешений.
        for (Role role : roles) {
            authorities.add(role);
        }
        // Возвращаем объект User из Spring Security, предоставляющий информацию о пользователе: Логин пользователя,
        // Пароль пользователя (уже зашифрованный), Список ролей (GrantedAuthority)
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

    }

}