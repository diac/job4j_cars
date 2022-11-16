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

    private static final String
            FIND_ALL_USERS_ORDER_BY_ID_QUERY = "FROM User ORDER BY id ASC";

    private static final String FIND_USER_BY_ID_QUERY = "FROM User WHERE id = :fId";

    private static final String FIND_USERS_BY_LIKE_LOGIN_QUERY = "FROM User WHERE login LIKE :fLogin";

    private static final String FIND_USER_BY_LOGIN_QUERY = "FROM User WHERE login = :fLogin";

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
                session.update(user);
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
                session.delete(new User(userId, null, null));
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
        Optional<User> result = query.uniqueResultOptional();
        session.close();
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
        Query<User> query = session.createQuery(FIND_USERS_BY_LIKE_LOGIN_QUERY, User.class);
        query.setParameter("fLogin", "%" + key + "%");
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
        Query<User> query = session.createQuery(FIND_USER_BY_LOGIN_QUERY);
        query.setParameter("fLogin", login);
        Optional<User> result = query.uniqueResultOptional();
        session.close();
        return result;
    }
}