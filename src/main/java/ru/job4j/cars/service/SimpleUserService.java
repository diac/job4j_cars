package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели User в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    /**
     * Получить список всех пользователей
     *
     * @return Список пользователей. Пустой список, если ничего не найдено.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Сохранить пользователя.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    /**
     * Обновить пользователя.
     *
     * @param user пользователь.
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(User user) {
        return userRepository.update(user);
    }

    /**
     * Удалить пользователя.
     *
     * @param user Пользователь, которого нужно удалить
     * @return true в случае удачного удаления. Иначе -- fals
     */
    @Override
    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    /**
     * Удалить пользователя по id.
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return userRepository.delete(id);
    }

    /**
     * Получить список пользователей, отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        return userRepository.findAllOrderById();
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    @Override
    public List<User> findByLikeLogin(String key) {
        return userRepository.findByLikeLogin(key);
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional объекта User.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}