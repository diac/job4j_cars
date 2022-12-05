package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Engine в БД
 */
@Repository
@AllArgsConstructor
public class HibernateEngineRepository implements EngineRepository {

    private static final String FIND_ALL_QUERY = "SELECT e FROM Engine e";

    private static final String FIND_BY_ID_QUERY = "SELECT e FROM Engine e WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Engine WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели Engine из БД
     *
     * @return Список двигателей. Пустой список, если ничего не найдено
     */
    @Override
    public List<Engine> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Engine.class);
    }

    /**
     * Получить один объект Engine из БД по id
     *
     * @param id Уникальный идентификатор объекта Engine
     * @return Optional для объекта Engine, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Engine> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Engine.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта Engine
     *
     * @param engine Объект Engine из которого создается новая запись в БД
     * @return Optional объекта Engine, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать (напр., из-за нарушения
     * ссылочной целостности)
     */
    @Override
    public Optional<Engine> add(Engine engine) {
        return crudRepository.optional(session -> {
            session.persist(engine);
            return engine;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту Engine
     *
     * @param engine Объект Engine, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Engine engine) {
        return crudRepository.execute(session -> {
            session.merge(engine);
            return true;
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Engine
     *
     * @param engine Объект Engine, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Engine engine) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", engine.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта Engine репозитории
     *
     * @param id Идентификатор объекта Engine, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}