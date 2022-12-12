package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.TransmissionType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели TransmissionType в БД
 */
@Repository
@AllArgsConstructor
public class HibernateTransmissionTypeRepository implements TransmissionTypeRepository {

    private static final String FIND_ALL_QUERY = "SELECT tt FROM TransmissionType tt";

    private static final String FIND_BY_ID_QUERY = "SELECT tt FROM TransmissionType tt WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM TransmissionType WHERE id = :fId";


    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели TransmissionType из БД
     * @return Список типов коробок передач. Пустой список, если ничего не найдено
     */
    @Override
    public List<TransmissionType> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, TransmissionType.class);
    }

    /**
     * Получить один объект TransmissionType из БД по id
     *
     * @param id Уникальный идентификатор объекта TransmissionType
     * @return Optional для объекта TransmissionType, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<TransmissionType> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                TransmissionType.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта TransmissionType
     *
     * @param transmissionType Объект TransmissionType из которого создается новая запись в БД
     * @return Optional объекта TransmissionType, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<TransmissionType> add(TransmissionType transmissionType) {
        return crudRepository.optional(session -> {
            session.persist(transmissionType);
            return transmissionType;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту TransmissionType
     *
     * @param transmissionType Объект TransmissionType, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(TransmissionType transmissionType) {
        return crudRepository.execute(session -> {
            TransmissionType merged = (TransmissionType) session.merge(transmissionType);
            return transmissionType.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту TransmissionType
     *
     * @param transmissionType Объект TransmissionType, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(TransmissionType transmissionType) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", transmissionType.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта TransmissionType в репозитории
     *
     * @param id Идентификатор объекта TransmissionType, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}