package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.SalesOrder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели SalesOrder в БД
 */
@Repository
@AllArgsConstructor
public class HibernateSalesOrderRepository implements SalesOrderRepository {

    private static final String FIND_ALL_QUERY = "SELECT so FROM SalesOrder so";

    private static final String FIND_BY_ID_QUERY = "SELECT so FROM SalesOrder so WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM SalesOrder WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели SalesOrder из БД
     * @return Список сделок. Пустой список, если ничего не найдено
     */
    @Override
    public List<SalesOrder> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, SalesOrder.class);
    }

    /**
     * Получить один объект SalesOrder из БД по id
     *
     * @param id Уникальный идентификатор объекта SalesOrder
     * @return Optional для объекта SalesOrder, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<SalesOrder> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                SalesOrder.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта SalesOrder
     *
     * @param salesOrder Объект SalesOrder из которого создается новая запись в БД
     * @return Optional объекта SalesOrder, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<SalesOrder> add(SalesOrder salesOrder) {
        return crudRepository.optional(session -> {
            session.persist(salesOrder);
            return salesOrder;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту SalesOrder
     *
     * @param salesOrder Объект SalesOrder, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(SalesOrder salesOrder) {
        return crudRepository.execute(session -> {
            SalesOrder merged = (SalesOrder) session.merge(salesOrder);
            return salesOrder.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту SalesOrder
     *
     * @param salesOrder Объект SalesOrder, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(SalesOrder salesOrder) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", salesOrder.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта SalesOrder в репозитории
     *
     * @param id Идентификатор объекта SalesOrder, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}