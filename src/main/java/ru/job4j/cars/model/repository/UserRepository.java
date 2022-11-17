package ru.job4j.cars.model.repository;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {

    private static final String
            FIND_ALL_USERS_ORDER_BY_ID_QUERY = "SELECT u FROM User u ORDER BY id ASC";

    private static final String FIND_USER_BY_ID_QUERY = "SELECT u FROM User u WHERE id = :fId";

    private static final String FIND_USERS_BY_LIKE_LOGIN_QUERY = "SELECT u FROM User u WHERE login LIKE :fLogin";

    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT u FROM User u WHERE login = :fLogin";

    private static final String UPDATE_USER_QUERY = """
            UPDATE
                User
            SET
                login = :fLogin,
                password = :fPassword
            WHERE
                id = :fId
            """;

    private static final String DELETE_USER_QUERY = "DELETE FROM User WHERE id = :fId";

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
                session.persist(user);
                session.getTransaction().commit();
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
                Query<User> query = session.createQuery(UPDATE_USER_QUERY);
                query.setParameter("fId", user.getId());
                query.executeUpdate();
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
                Query<User> query = session.createQuery(DELETE_USER_QUERY);
                query.setParameter("fId", userId);
                query.executeUpdate();
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
        List<User> users = new ArrayList<>();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                Query<User> query = session.createQuery(FIND_ALL_USERS_ORDER_BY_ID_QUERY, User.class);
                users = query.getResultList();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        Optional<User> result = Optional.empty();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                Query<User> query = session.createQuery(FIND_USER_BY_ID_QUERY, User.class)
                        .setParameter("fId", id);
                result = query.uniqueResultOptional();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        List<User> users = new ArrayList<>();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                Query<User> query = session.createQuery(FIND_USERS_BY_LIKE_LOGIN_QUERY, User.class)
                        .setParameter("fLogin", "%" + key + "%");
                users = query.getResultList();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
        return users;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Optional<User> result = Optional.empty();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                Query<User> query = session.createQuery(FIND_USER_BY_LOGIN_QUERY, User.class)
                        .setParameter("fLogin", login);
                result = query.uniqueResultOptional();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
}