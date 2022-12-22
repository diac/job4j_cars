package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за реализацию операций CRUD для объектов Brand в БД
 */
@Repository
@AllArgsConstructor
public class HibernateBrandRepository implements BrandRepository {

    private static final String FIND_ALL_QUERY = "SELECT b FROM Brand b";

    private static final String FIND_BY_ID_QUERY = "SELECT b FROM Brand b WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Brand WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели Brand из БД
     * @return Список марок. Пустой список, если ничего не найдено
     */
    @Override
    public List<Brand> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Brand.class);
    }

    /**
     * Получить один объект Brand из БД по id
     *
     * @param id Уникальный идентификатор объекта Brand
     * @return Optional для объекта Brand, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Brand> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Brand.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта Brand
     *
     * @param brand Объект Brand из которого создается новая запись в БД
     * @return Optional объекта Brand, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<Brand> add(Brand brand) {
        return crudRepository.optional(session -> {
            session.persist(brand);
            return brand;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту Brand
     *
     * @param brand Объект Brand, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Brand brand) {
        return crudRepository.execute(session -> {
            Brand merged = (Brand) session.merge(brand);
            return brand.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Brand
     *
     * @param brand Объект Brand, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Brand brand) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", brand.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта Brand в репозитории
     *
     * @param id Идентификатор объекта Brand, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}