package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Driver в БД
 */
@Repository
@AllArgsConstructor
public class HibernateDriverRepository implements DriverRepository {

    private static final String FIND_ALL_QUERY = "SELECT d FROM Driver d";

    private static final String FIND_BY_ID_QUERY = "SELECT d FROM Driver d WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Driver WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели Driver из БД
     *
     * @return Список водителей. Пустой список, если ничего не найдено
     */
    @Override
    public List<Driver> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Driver.class);
    }

    /**
     * Получить один объект Driver из БД по id
     *
     * @param id Уникальный идентификатор объекта Driver
     * @return Optional для объекта Driver, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Driver> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Driver.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта Driver
     *
     * @param driver Объект Driver из которого создается новая запись в БД
     * @return Optional объекта Driver, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать (напр., из-за нарушения
     * ссылочной целостности)
     */
    @Override
    public Optional<Driver> add(Driver driver) {
        return crudRepository.optional(session -> {
            session.persist(driver);
            return driver;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту Driver
     *
     * @param driver Объект Driver, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Driver driver) {
        return crudRepository.execute(session -> {
            Driver merged = (Driver) session.merge(driver);
            return driver.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Driver
     *
     * @param driver Объект Driver, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Driver driver) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", driver.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта Driver в репозитории
     *
     * @param id Идентификатор объекта Driver, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}