package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * // Этот метод возвращает (коллекцию) объектов типа User (пользователей) из базы данных. Он вызывает метод findAll() у UserRepository,
     * // который в свою очередь выполняет запрос к базе данных для извлечения всех записей из таблицы, представляющей сущность User.
     **/
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * // Этот метод позволяет найти пользователя по его id. Он вызывает метод findById() у UserRepository, который выполняет запрос к базе данных для поиска записи с указанным id.
     * // Результатом является Optional<User>, который содержит найденного пользователя или пустое значение, если пользователь не найден.
     **/
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Метод отмечен аннотацией @Transactional,
    // что указывает Spring на необходимость управления транзакцией вокруг этого метода.(подробнее тут: https://habr.com/ru/articles/682362/)
    // Этот метод обновляет информацию о пользователе и сохраняет её.
    @Override
    @Transactional
    public void updateUser(User user) {
        //user.setId(user.getId());
        userRepository.save(user);

    }

    /**
     * // Это метод получает id пользователя в качестве параметра. Затем использует userRepository для поиска пользователя в БД по указанному id.
     * // Результат поиска оборачивается в Optional, что позволяет более безопасно обрабатывать ситуации, когда пользователь может быть не найден.
     * // Метод orElse(null) возвращает найденного пользователя, если он был найден, или null, если пользователь с указанным идентификатором не существует в базе данных.
     **/
    @Override
    public User getOneUser(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElse(null);
    }

    // Метод save() у UserRepository используется для сохранения данных пользователя в БД. Если пользователь уже существует в БД, то он будет обновлен, иначе будет создан новый пользователь.
    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    // Метод deleteById() у UserRepository используется для удаления пользователя из БД.
    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // userRepository.findByUsername(username); - используется метод findByUsername репозитория, который является стандартным методом Spring Data JPA для поиска записей в базе данных на основе имени пользователя (username).
    @Override
    @Query("SELECT u FROM User u join FETCH u.roles WHERE u.username = :email")
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * // @Query("SELECT u FROM User u join FETCH u.roles WHERE u.username = :email") - Эта строка запроса выполняет выборку объектов User из базы данных,
     * // объединяя их с ассоциированными сущностями roles с помощью жадной загрузки. Результаты выборки ограничиваются теми объектами, у которых поле username совпадает с переданным значением параметра :email.
     * // - Query: Эта аннотация указывает Spring Data JPA, что метод будет использовать пользовательский запрос.
     * // - "SELECT u FROM User u join FETCH u.roles WHERE u.username = :email": Это строка запроса, которая определяет операцию выборки данных
     * //  -- SELECT u: Здесь u является псевдонимом (alias) для сущности User, которая определена в классе User. Это указывает, что мы выбираем объекты User.
     * //  -- FROM User u: Здесь User - это имя сущности (сущности, связанной с таблицей в базе данных), а u - это псевдоним для этой сущности.
     * //  -- join FETCH u.roles: Здесь мы объединяем (join) сущность User с ее ассоциированными сущностями roles.
     * //  -- Ключевое слово FETCH используется для выполнения жадной (eager) загрузки связанных сущностей. Это позволяет избежать проблемы ленивой (lazy) загрузки данных.
     * //  -- WHERE u.username = :email: Это условие, по которому мы фильтруем результаты выборки. Здесь мы выбираем только те объекты User, у которых значение поля username совпадает с переданным значением :email.
     * //  -- Здесь :email - это параметр, который будет связан с аргументом метода findByUsername(String username).
     **/


}