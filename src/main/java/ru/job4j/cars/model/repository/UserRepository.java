package ru.job4j.cars.model.repository;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {

    private static final String CREATE_USER_QUERY = "INSERT INTO User (login, password) VALUES (:fLogin, :fPassword)";

    private static final String UPDATE_USER_QUERY = """
            UPDATE
                User (login, password)
            SET
                login = :fLogin,
                password = :fPassword
            WHERE
                id = :fId;
            """;

    private static final String DELETE_USER_QUERY = "DELETE FROM User WHERE id = :fId";

    private static final String
            FIND_ALL_USERS_ORDER_BY_ID_QUERY = "SELECT * FROM User ORDER BY id ASC";

    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM User WHERE id = :fId";

    private static final String FIND_USERS_BY_LIKE_LOGIN_QUERY = "SELECT * FROM User WHERE login LIKE '%:fLogin%'";

    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT * FROM User Where login = :fLogin";

    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                session.createQuery(CREATE_USER_QUERY, User.class)
                        .setParameter("fLogin", user.getLogin())
                        .setParameter("fPassword", user.getPassword())
                        .executeUpdate();
                session.getTransaction().commit();
                Integer insertedId = (Integer) session.createSQLQuery("SELECT LAST_INSERT_ID()")
                        .uniqueResult();
                user.setId(insertedId);
            } catch (HibernateException e) {
                session.getTransaction().rollback();
            }
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                session.createQuery(UPDATE_USER_QUERY, User.class)
                        .setParameter("fId", user.getId())
                        .setParameter("fLogin", user.getLogin())
                        .setParameter("fPassword", user.getPassword())
                        .executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                session.createQuery(DELETE_USER_QUERY, User.class)
                        .setParameter("fId", userId)
                        .executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_ALL_USERS_ORDER_BY_ID_QUERY, User.class);
        List<User> users = query.getResultList();
        session.close();
        return users;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_USER_BY_ID_QUERY)
                .setParameter("fId", id);
        Optional<User> result = Optional.ofNullable(query.uniqueResult());
        sf.close();
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_USERS_BY_LIKE_LOGIN_QUERY, User.class)
                .setParameter("fLogin", key);
        List<User> users = query.getResultList();
        session.close();
        return users;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_USER_BY_LOGIN_QUERY)
                .setParameter("fLogin", login);
        Optional<User> result = Optional.ofNullable(query.uniqueResult());
        sf.close();
        return result;
    }
}