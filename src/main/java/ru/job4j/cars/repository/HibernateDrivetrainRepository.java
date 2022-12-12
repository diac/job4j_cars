package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Drivetrain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Drivetrain в БД
 */
@Repository
@AllArgsConstructor
public class HibernateDrivetrainRepository implements DrivetrainRepository {

    private static final String FIND_ALL_QUERY = "SELECT d FROM Drivetrain d";

    private static final String FIND_BY_ID_QUERY = "SELECT d FROM Drivetrain d WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Drivetrain WHERE id = :fId";

    private final CrudRepository crudRepository;

    /**
     * Получить все записи для модели Drivetrain из БД
     * @return Список типов приводов. Пустой список, если ничего не найдено
     */
    @Override
    public List<Drivetrain> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Drivetrain.class);
    }

    /**
     * Получить один объект Drivetrain из БД по id
     *
     * @param id Уникальный идентификатор объекта Drivetrain
     * @return Optional для объекта Drivetrain, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Drivetrain> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Drivetrain.class,
                Map.of("fId", id)
        );
    }

    /**
     * Добавить новую запись в БД из объекта Drivetrain
     *
     * @param drivetrain Объект Drivetrain из которого создается новая запись в БД
     * @return Optional объекта Drivetrain, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    @Override
    public Optional<Drivetrain> add(Drivetrain drivetrain) {
        return crudRepository.optional(session -> {
            session.persist(drivetrain);
            return drivetrain;
        });
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту Drivetrain
     *
     * @param drivetrain Объект Drivetrain, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Drivetrain drivetrain) {
        return crudRepository.execute(session -> {
            Object merged = session.merge(drivetrain);
            return merged.equals(drivetrain);
        });
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Drivetrain
     *
     * @param drivetrain Объект Drivetrain, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Drivetrain drivetrain) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", drivetrain.getId()));
    }

    /**
     * Удалить из БД запись, по id объекта Drivetrain в репозитории
     *
     * @param id Идентификатор объекта Drivetrain, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}