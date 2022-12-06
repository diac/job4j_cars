package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Post в БД
 */
@Repository
@AllArgsConstructor
public class HibernatePostRepository  implements PostRepository {

    private static final String FIND_ALL_QUERY = "SELECT p FROM Post p";

    private static final String FIND_BY_ID_QUERY = "SELECT p FROM Post p WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Post WHERE id = :fId";

    private static final String FIND_ALL_RECENT_QUERY
            = "SELECT p FROM Post p WHERE created BETWEEN TIMESTAMP 'yesterday' AND NOW()";

    private static final String FIND_ALL_WITH_PHOTO_QUERY = "SELECT p FROM Post p WHERE photo IS NOT NULL";

    private static final String FIND_ALL_BY_CAR_ID_QUERY = "SELECT p FROM Post p WHERE car_id = :fCarId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели Post из БД
     *
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Post.class);
    }

    /**
     * Получить один объект Post из БД по id
     *
     * @param id Уникальный идентификатор объекта Post
     * @return Optional для объекта Post, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Post.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта Post
     *
     * @param post Объект Post из которого создается новая запись в БД
     * @return Optional объекта Post, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать (напр., из-за нарушения
     * ссылочной целостности)
     */
    @Override
    public Optional<Post> add(Post post) {
        return crudRepository.optional(session -> {
            session.persist(post);
            return post;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту Post
     *
     * @param post Объект Post, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Post post) {
        return crudRepository.execute(session -> {
            session.merge(post);
            return true;
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Post
     *
     * @param post Объект Post, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Post post) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", post.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта Post в репозитории
     *
     * @param id Идентификатор объекта Post, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }

    /**
     * Получить все объявления за последний день
     *
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllRecent() {
        return crudRepository.query(FIND_ALL_RECENT_QUERY, Post.class);
    }

    /**
     * Получить все объявления с фото
     *
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllWithPhoto() {
        return crudRepository.query(FIND_ALL_WITH_PHOTO_QUERY, Post.class);
    }

    /**
     * Получить все объявления для определенной машины
     *
     * @param car Машина, для которой ищутся объявления
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllByCar(Car car) {
        return crudRepository.query(
                FIND_ALL_BY_CAR_ID_QUERY,
                Post.class,
                Map.of("fCarId", car.getId())
        );
    }
}