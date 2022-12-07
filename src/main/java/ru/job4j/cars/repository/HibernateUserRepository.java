package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final String FIND_ALL_QUERY = "SELECT u FROM User";

    private static final String
            FIND_ALL_USERS_ORDER_BY_ID_QUERY = "SELECT u FROM User u ORDER BY id ASC";

    private static final String FIND_USER_BY_ID_QUERY = "SELECT u FROM User u WHERE id = :fId";

    private static final String FIND_USERS_BY_LIKE_LOGIN_QUERY = "SELECT u FROM User u WHERE login LIKE :fLogin";

    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT u FROM User u WHERE login = :fLogin";

    private static final String FIND_BY_LOGIN_AND_PASSWORD_QUERY
            = "SELECT u FROM User u WHERE login = :fLogin AND password = :fPassword";

    private static final String DELETE_USER_QUERY = "DELETE FROM User WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить список всех пользователей
     *
     * @return Список пользователей. Пустой список, если ничего не найдено.
     */
    @Override
    public List<User> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, User.class);
    }

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> add(User user) {
        return crudRepository.optional(session -> {
            session.persist(user);
            return user;
        });
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(User user) {
        return crudRepository.execute(session -> {
            session.merge(user);
            return true;
        });
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int userId) {
        return crudRepository.execute(DELETE_USER_QUERY, Map.of("fId", userId));
    }

    /**
     * Удалить пользователя.
     *
     * @param user Пользователь, которого нужно удалить
     * @return true в случае удачного удаления. Иначе -- fals
     */
    @Override
    public boolean delete(User user) {
        return crudRepository.execute(DELETE_USER_QUERY, Map.of("fId", user.getId()));
    }

    /**
     * Список пользователей, отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query(FIND_ALL_USERS_ORDER_BY_ID_QUERY, User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                FIND_USER_BY_ID_QUERY,
                User.class,
                Map.of("fId", id)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                FIND_USERS_BY_LIKE_LOGIN_QUERY,
                User.class,
                Map.of("fLogin", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                FIND_USER_BY_LOGIN_QUERY,
                User.class,
                Map.of("fLogin", login)
        );
    }

    /**
     * Получить один объект User из БД по значению полей login и password
     *
     * @param login    Логин пользователя в системе
     * @param password Пароль пользователя в системе
     * @return Optional для объекта User, если в БД существует запись для переданных значений login и password.
     * Иначе -- Optional.empty()
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                FIND_BY_LOGIN_AND_PASSWORD_QUERY,
                User.class,
                Map.of(
                        "fLogin", login,
                        "fPassword", password
                )
        );
    }
}