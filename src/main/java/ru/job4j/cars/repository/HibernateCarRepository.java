package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Car в БД
 */
@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {

    private static final String FIND_ALL_QUERY = "SELECT c FROM Car c";

    private static final String FIND_BY_ID_QUERY = "SELECT c FROM Car c WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Car WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели Car из БД
     *
     * @return Список машин. Пустой список, если ничего не найдено
     */
    @Override
    public List<Car> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Car.class);
    }

    /**
     * Получить один объект Car из БД по id
     *
     * @param id Уникальный идентификатор объекта Car
     * @return Optional для объекта Car, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Car.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта Car
     *
     * @param car Объект Car из которого создается новая запись в БД
     * @return Optional объекта Car, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать (напр., из-за нарушения
     * ссылочной целостности)
     */
    @Override
    public Optional<Car> add(Car car) {
        return crudRepository.optional(session -> {
            session.persist(car);
            return car;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту Car
     *
     * @param car Объект Car, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Car car) {
        return crudRepository.execute(session -> {
            Car merged = (Car) session.merge(car);
            return car.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Car
     *
     * @param car Объект Car, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Car car) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", car.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта Car в репозитории
     *
     * @param id Идентификатор объекта Car, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}