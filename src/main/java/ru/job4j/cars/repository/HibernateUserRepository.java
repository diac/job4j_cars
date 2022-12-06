package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateUserRepository {

    private static final String
            FIND_ALL_USERS_ORDER_BY_ID_QUERY = "SELECT u FROM User u ORDER BY id ASC";

    private static final String FIND_USER_BY_ID_QUERY = "SELECT u FROM User u WHERE id = :fId";

    private static final String FIND_USERS_BY_LIKE_LOGIN_QUERY = "SELECT u FROM User u WHERE login LIKE :fLogin";

    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT u FROM User u WHERE login = :fLogin";

    private static final String DELETE_USER_QUERY = "DELETE FROM User WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        crudRepository.run(DELETE_USER_QUERY, Map.of("fId", userId));
    }

    /**
     * Список пользователь отсортированных по id.
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
}