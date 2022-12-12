package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyStyle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели BodyStyle в БД
 */
@Repository
@AllArgsConstructor
public class HibernateBodyStyleRepository implements BodyStyleRepository {

    private static final String FIND_ALL_QUERY = "SELECT bs FROM BodyStyle bs";

    private static final String FIND_BY_ID_QUERY = "SELECT bs FROM BodyStyle bs WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM BodyStyle WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели BodyStyle из БД
     * @return Список типов кузовов. Пустой список, если ничего не найдено
     */
    @Override
    public List<BodyStyle> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, BodyStyle.class);
    }

    /**
     * Получить один объект BodyStyle из БД по id
     *
     * @param id Уникальный идентификатор объекта BodyStyle
     * @return Optional для объекта BodyStyle, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<BodyStyle> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                BodyStyle.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта BodyStyle
     *
     * @param bodyStyle Объект BodyStyle из которого создается новая запись в БД
     * @return Optional объекта BodyStyle, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<BodyStyle> add(BodyStyle bodyStyle) {
        return crudRepository.optional(session -> {
            session.persist(bodyStyle);
            return bodyStyle;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту BodyStyle
     *
     * @param bodyStyle Объект BodyStyle, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(BodyStyle bodyStyle) {
        return crudRepository.execute(session -> {
            session.merge(bodyStyle);
            return true;
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту BodyStyle
     *
     * @param bodyStyle Объект BodyStyle, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(BodyStyle bodyStyle) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", bodyStyle.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта BodyStyle в репозитории
     *
     * @param id Идентификатор объекта BodyStyle, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}