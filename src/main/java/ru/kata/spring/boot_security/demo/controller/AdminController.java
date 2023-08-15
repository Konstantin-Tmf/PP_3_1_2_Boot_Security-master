package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.entities.User;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * // Аннотация @GetMapping() с пустыми скобками указывает на то, что метод будет обрабатывать HTTP GET запросы для указанного маршрута (URL).
     * // Когда скобки оставлены пустыми, это означает, что метод будет срабатывать для самого базового пути (например, /users) без дополнительных параметров в URL.
     * // 'Model model' - предоставляет способ добавления атрибутов к модели данных.
     * // Модель используется для передачи данных с контроллера в представление (View).
     * // 'Principal principal' - Это параметр, предоставляющий информацию о текущем аутентифицированном пользователе.
     * // Далее мы получаем объект класса User, и principal.getName() возвращает имя текущего аутентифицированного пользователя.
     * // Затем данные о пользователе добавляются в Модель как Атрибуты - model.addAttribute("users", user);
     * // Это позволяет передать данные о текущем пользователе в представление, где эти атрибуты могут быть использованы для отображения информации о пользователе.
     * // Далее в Модель добавляем  всех пользователей и все их роли.
     * // Методы userService.getAllUsers() и roleService.getAllRoles() возвращают списки всех пользователей и всех ролей соответственно.
     **/
    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("users", user);
        model.addAttribute("email", user);
        model.addAttribute("allUser", userService.getAllUsers());
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "admin/userManagement";
    }

    /**
     * // @ModelAttribute("user") User user аннотирован как @ModelAttribute("user"), что означает, что объект user должен быть взят из Модели. Это может быть объект класса User,
     * // который представляет собой модель данных для нового пользователя.
     * // @ModelAttribute("roles") Role role - этот параметр указывает, что объект role должен быть взят из Модели,он представляет роли пользователя.
     * // model.addAttribute("roleUser", roleService.getAllRoles()); - добавление атрибута "roleUser" в Модель.
     * // Значение этого атрибута получается вызовом roleService.getAllRoles(), чтобы загрузить список всех возможных ролей пользователей.
     **/
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model, @ModelAttribute("roles") Role role) {
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "admin/userManagement";
    }

    /**
     * // @PostMapping(): - Эта аннотация указывает, что метод должен обрабатывать HTTP POST запросы.
     * // Метод принимает два параметра: @ModelAttribute("user") User user: Объект user получается из Модели, и он содержит данные, которые были введены в форме, когда мы создаём пользователя.
     * // @RequestParam(name = "role", defaultValue = "0") Long[] id: Этот параметр получает массив id из параметра запроса role.
     * // Здесь name = "role" указывает, что метод ожидает параметр с именем "role" из запроса.
     * // Если параметр не будет передан, устанавливается значение по умолчанию - массив с одним элементом "0".
     * //
     * // Set<Role> roles = new HashSet<>(roleService.getRoles(id));: В этой строке создается HashSet<> ролей (Set<Role>), которые будут назначены новому пользователю
     * // .roleService нужен для получения ролей на основе массива id, полученного из параметра запроса.
     * // user.setRoles(roles);: Здесь устанавливаются роли для пользователя. Роли, созданные на предыдущем шаге, присваивается пользователю через метод setRoles.
     * // user.setPassword(passwordEncoder.encode(user.getPassword()));: Пароль пользователя кодируется с использованием passwordEncoder.
     **/
    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user, @RequestParam(name = "role", defaultValue = "0") Long[] id) {
        Set<Role> roles = new HashSet<>(roleService.getRoles(id));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);

        return "redirect:/admin";
    }

    /**
     * // @PatchMapping("/edit/{id}"): Эта аннотация указывает, что метод будет обрабатывать HTTP PATCH запросы для определенного URL, где {id} представляет идентификатор пользователя, который будет редактироваться.
     * // Метод принимает два параметра: @ModelAttribute("user") User user: Объект user получается из Модели и содержит данные, которые были введены в форме редактирования пользователя.
     * // @RequestParam(name = "role", defaultValue = "0") Long[] id: Этот параметр получает массив id из параметра запроса role - это роли для пользователя во время редактирования.
     * // Если параметр не будет передан, устанавливается значение по умолчанию - массив с одним элементом "0".
     * // Set<Role> roles = new HashSet<>(roleService.getRoles(id));: В этой строке создается множество ролей (Set<Role>), которые будут назначены отредактированному пользователю.
     * // Он использует сервис roleService для получения ролей на основе массива id, полученного из параметра запроса.
     * // user.setRoles(roles);: Здесь обновляются роли пользователя. Роли, созданные на предыдущем шаге(Set<Role>), устанавливаются для пользователя через метод setRoles.
     * // user.setPassword(passwordEncoder.encode(user.getPassword()));: Пароль пользователя кодируется с использованием passwordEncoder.Это необязательно будет выполняться в случае, если пароль не изменился.
     * // userService.updateUser(user);: Обновленный объект пользователя сохраняется с использованием userService, чтобы обновить информацию о пользователе в базе данных.
     **/
    @PatchMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user, @RequestParam(name = "role", defaultValue = "0") Long[] id) {
        Set<Role> roles = new HashSet<>(roleService.getRoles(id));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.updateUser(user);
        return "redirect:/admin";
    }

    /**
     * // При получении DELETE-запроса с URL вида /delete/{id}, где {id} - это конкретный идентификатор пользователя для удаления, метод будет вызван.
     * // Аннотация @PathVariable("id") используется для извлечения значения идентификатора из URL и привязки его к параметру метода id.
     * // Метод userService.deleteUser(id) передает идентификатор пользователя в метод userService - а, в он в свою очередь обращается к базе данных и удаляет пользователя с указанным идентификатором.
     **/
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}