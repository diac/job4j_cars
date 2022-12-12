package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.ExteriorColor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели ExteriorColor в БД
 */
@Repository
@AllArgsConstructor
public class HibernateExteriorColorRepository implements ExteriorColorRepository {

    private static final String FIND_ALL_QUERY = "SELECT ec FROM ExteriorColor ec";

    private static final String FIND_BY_ID_QUERY = "SELECT ec FROM ExteriorColor ec WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM ExteriorColor WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели ExteriorColor из БД
     * @return Список типов цветов кузовов. Пустой список, если ничего не найдено
     */
    @Override
    public List<ExteriorColor> findAll() {
        return crudRepository.query(
                FIND_ALL_QUERY,
                ExteriorColor.class
        );
    }

    /**
     * Получить один объект ExteriorColor из БД по id
     *
     * @param id Уникальный идентификатор объекта ExteriorColor
     * @return Optional для объекта ExteriorColor, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<ExteriorColor> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                ExteriorColor.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта ExteriorColor
     *
     * @param exteriorColor Объект ExteriorColor из которого создается новая запись в БД
     * @return Optional объекта ExteriorColor, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<ExteriorColor> add(ExteriorColor exteriorColor) {
        return crudRepository.optional(session -> {
            session.persist(exteriorColor);
            return exteriorColor;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту ExteriorColor
     *
     * @param exteriorColor Объект ExteriorColor, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(ExteriorColor exteriorColor) {
        return crudRepository.execute(session -> {
            ExteriorColor merged = (ExteriorColor) session.merge(exteriorColor);
            return exteriorColor.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту ExteriorColor
     *
     * @param exteriorColor Объект ExteriorColor, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(ExteriorColor exteriorColor) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", exteriorColor.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта ExteriorColor в репозитории
     *
     * @param id Идентификатор объекта ExteriorColor, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}