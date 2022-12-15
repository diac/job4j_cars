package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Post в репозитории
 */
@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    /**
     * Получить список всех объявлений
     *
     * @return Список объявлений. Пустой список, если ничего не найдено.
     */
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * Найти объявление по ID
     *
     * @return объявление
     */
    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    /**
     * Сохранить объявление
     *
     * @param post объявление
     * @return Объявление с id
     */
    @Override
    public Optional<Post> add(Post post) {
        post.setCreated(LocalDateTime.now());
        post.setAvailable(true);
        return postRepository.add(post);
    }

    /**
     * Обновить объявление
     *
     * @param post Объявление
     * @param user Пользователь, который пытается обновить объявление
     * @return true в случае удачного обновления. Иначе -- false
     * @throws IllegalArgumentException В случае, если пользователь попытается обновить чужое объявление
     */
    @Override
    public boolean update(Post post, User user) {
        if (!user.equals(post.getUser())) {
            throw new IllegalArgumentException(String.format(
                    "Не удалось обновить объявление. Пользователь %s не является автором объявления %d",
                    user.getLogin(),
                    post.getId()
            ));
        }
        return postRepository.update(post);
    }

    /**
     * Удалить объявление
     *
     * @param post Объявление
     * @param user Пользователь, который пытается удалить объявление
     * @return true в случае удачного удаления. Иначе -- false
     * @throws IllegalArgumentException В случае, если пользователь попытается удалить чужое объявление
     */
    @Override
    public boolean delete(Post post, User user) {
        if (!user.equals(post.getUser())) {
            throw new IllegalArgumentException(String.format(
                    "Не удалось удалить объявление. Пользователь %s не является автором объявления %d",
                    user.getLogin(),
                    post.getId()
            ));
        }
        return postRepository.delete(post);
    }

    /**
     * Удалить объявление по ID
     *
     * @param id ID объявления
     * @param user Пользователь, который пытается удалить объявление
     * @return true в случае удачного удаления. Иначе -- false
     * @throws IllegalArgumentException В случае, если пользователь попытается удалить чужое объявление
     */
    @Override
    public boolean delete(int id, User user) {
        Post post = postRepository.findById(id).orElse(new Post());
        if (!user.equals(post.getUser())) {
            throw new IllegalArgumentException(String.format(
                    "Не удалось удалить объявление. Пользователь %s не является автором объявления %d",
                    user.getLogin(),
                    id
            ));
        }
        return postRepository.delete(id);
    }

    /**
     * Найти все объявления в диапазоне дат
     *
     * @param dateFrom Минимальное значение диапазона дат
     * @param dateTo   Максимальное значение диапазона дат
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllInDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return postRepository.findAllInDateRange(dateFrom, dateTo);
    }

    /**
     * Получить все объявления с фото
     *
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllWithPhoto() {
        return postRepository.findAllWithPhoto();
    }

    /**
     * Получить все объявления для определенной машины
     *
     * @param car Машина, для которой ищутся объявления
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllByCar(Car car) {
        return postRepository.findAllByCar(car);
    }
}