package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PostSearchParams;
import ru.job4j.cars.model.PostSearchResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Post в БД
 */
@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private static final String FIND_ALL_QUERY = "SELECT p FROM Post p";

    private static final String FIND_ALL_BY_USER_ID_QUERY = "SELECT p FROM Post p WHERE p.user.id = :fUserId";

    private static final String FIND_BY_ID_QUERY = "SELECT p FROM Post p WHERE id = :fId";

    private static final String FIND_BY_ID_WITH_PARTICIPATES_QUERY
            = "SELECT p FROM Post p LEFT JOIN FETCH p.participates WHERE p.id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Post WHERE id = :fId";

    private static final String FIND_ALL_IN_DATE_RANGE_QUERY
            = "SELECT p FROM Post p WHERE created BETWEEN :fDateFrom AND :fDateTo";

    private static final String FIND_ALL_WITH_PHOTO_QUERY = "SELECT p FROM Post p WHERE photo IS NOT NULL";

    private static final String FIND_ALL_BY_CAR_ID_QUERY = "SELECT p FROM Post p WHERE car_id = :fCarId";

    private final CrudRepository crudRepository;

    private final PostSearchResultRepository postSearchResultRepository;

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
     * Получить все записи для модели Post из БД по ID пользователя
     *
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllByUserId(int userId) {
        return crudRepository.query(
                FIND_ALL_BY_USER_ID_QUERY,
                Post.class,
                Map.of("fUserId", userId)
        );
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
     * Получить один объект Post из БД по id, включая данные подписанных на объявление пользователей
     *
     * @param id Уникальный идентификатор объекта Post
     * @return Optional для объекта Post, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Post> findByIdWithParticipates(int id) {
        return crudRepository.optional(
                FIND_BY_ID_WITH_PARTICIPATES_QUERY,
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
            Post merged = (Post) session.merge(post);
            return post.equals(merged);
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
     * Найти все объявления в диапазоне дат
     *
     * @param dateFrom Минимальное значение диапазона дат
     * @param dateTo   Максимальное значение диапазона дат
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllInDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return crudRepository.query(
                FIND_ALL_IN_DATE_RANGE_QUERY,
                Post.class,
                Map.of(
                        "fDateFrom", dateFrom,
                        "fDateTo", dateTo
                )
        );
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

    /**
     * Получить все объявления, удовлетворяющие условиям поиска
     *
     * @param postSearchParams Объект, содержащий условия поиска
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> search(PostSearchParams postSearchParams) {
        List<Integer> searchResultIds = postSearchResultRepository.search(postSearchParams)
                .stream()
                .map(PostSearchResult::getPostId)
                .toList();
        return crudRepository.tx(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Post> cq = cb.createQuery(Post.class);
            Root<Post> postRoot = cq.from(Post.class);
            cq.select(postRoot).where(cb.and(
                    postRoot.get("id").in(searchResultIds),
                    cb.equal(postRoot.get("available"), true)
            ));
            Query<Post> query = session.createQuery(cq);
            return query.getResultList();
        });
    }
}