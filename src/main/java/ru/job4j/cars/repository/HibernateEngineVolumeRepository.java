package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.EngineVolume;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели EngineVolume в БД
 */
@Repository
@AllArgsConstructor
public class HibernateEngineVolumeRepository implements EngineVolumeRepository {

    private static final String FIND_ALL_QUERY = "SELECT ev FROM EngineVolume ev";

    private static final String FIND_BY_ID_QUERY = "SELECT ev FROM EngineVolume ev WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM EngineVolume WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели EngineVolume из БД
     * @return Список объемов двигателей. Пустой список, если ничего не найдено
     */
    @Override
    public List<EngineVolume> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, EngineVolume.class);
    }

    /**
     * Получить один объект EngineVolume из БД по id
     *
     * @param id Уникальный идентификатор объекта EngineVolume
     * @return Optional для объекта EngineVolume, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<EngineVolume> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                EngineVolume.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта EngineVolume
     *
     * @param engineVolume Объект EngineVolume из которого создается новая запись в БД
     * @return Optional объекта EngineVolume, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<EngineVolume> add(EngineVolume engineVolume) {
        return crudRepository.optional(session -> {
            session.persist(engineVolume);
            return engineVolume;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту EngineVolume
     *
     * @param engineVolume Объект EngineVolume, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(EngineVolume engineVolume) {
        return crudRepository.execute(session -> {
            EngineVolume merged = (EngineVolume) session.merge(engineVolume);
            return engineVolume.equals(merged);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту EngineVolume
     *
     * @param engineVolume Объект EngineVolume, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(EngineVolume engineVolume) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", engineVolume.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта EngineVolume в репозитории
     *
     * @param id Идентификатор объекта EngineVolume, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}