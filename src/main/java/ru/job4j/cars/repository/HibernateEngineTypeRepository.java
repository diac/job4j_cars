package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.EngineType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за реализацию операций CRUD для объектов EngineType в БД
 */
@Repository
@AllArgsConstructor
public class HibernateEngineTypeRepository implements EngineTypeRepository {

    private static final String FIND_ALL_QUERY = "SELECT et FROM EngineType et";

    private static final String FIND_BY_ID_QUERY = "SELECT et FROM EngineType et WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM EngineType WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели EngineType из БД
     * @return Список типов двигателей. Пустой список, если ничего не найдено
     */
    @Override
    public List<EngineType> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, EngineType.class);
    }

    /**
     * Получить один объект EngineType из БД по id
     *
     * @param id Уникальный идентификатор объекта EngineType
     * @return Optional для объекта EngineType, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<EngineType> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                EngineType.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта EngineType
     *
     * @param engineType Объект EngineType из которого создается новая запись в БД
     * @return Optional объекта EngineType, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<EngineType> add(EngineType engineType) {
        return crudRepository.optional(session -> {
            session.persist(engineType);
            return engineType;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту EngineType
     *
     * @param engineType Объект EngineType, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(EngineType engineType) {
        return crudRepository.execute(session -> {
            EngineType merged = (EngineType) session.merge(engineType);
            return engineType.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту EngineType
     *
     * @param engineType Объект EngineType, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(EngineType engineType) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", engineType.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта EngineType в репозитории
     *
     * @param id Идентификатор объекта EngineType, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}