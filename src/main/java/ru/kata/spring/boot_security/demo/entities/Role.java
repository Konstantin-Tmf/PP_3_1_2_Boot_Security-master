package ru.kata.spring.boot_security.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id")
    // Когда мы сохраняем новую сущность в базе данных, и у этой сущности есть поле, помеченное как первичный ключ и аннотированное как @GeneratedValue(strategy = GenerationType.IDENTITY),
    // база данных будет автоматически генерировать уникальные значения для этого поля. Таким образом, вам не нужно беспокоиться о генерации уникальных идентификаторов вручную.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public Role(Long id) {
        this.id = id;
    }

    public Role(String role) {
        this.role = role;
    }

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    // У нас есть строка role, которая, содержит имя роли пользователя, начинающееся с префикса.
    // Метод substring(5) вызывается на этой строке. Он берет подстроку, начиная с индекса 5 (шестой символ в строке), и до конца строки.
    // Это значит, что первые 5 символов будут отсечены.
    // Например, если role содержит "ROLE_ADMIN", то после вызова метода getRoleWithoutPrefix() вернется строка "ADMIN".
    // Если role содержит "ROLE_USER", то метод вернет "USER".
    public String getRoleWithoutPrefix() {
        return role.substring(5);
    }

    // Когда Spring Security аутентифицирует пользователя и проверяет его доступ к различным частям приложения, он использует коллекцию объектов, реализующих GrantedAuthority,
    // чтобы определить, какие права имеет данный конкретный пользователь.
    // В нашем случае, этот метод возвращает имя роли пользователя, которая представляет его права в системе авторизации Spring Security.
    @Override
    public String getAuthority() {
        return getRole();
    }

    // В данном случае, метод возвращает значение поля role, которое, содержит имя роли (например, "ROLE_USER", "ROLE_ADMIN").
    @Override
    public String toString() {
        return this.role;
    }


}
