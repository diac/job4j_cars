package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.EngineType;
import ru.job4j.cars.repository.EngineTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели EngineType в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleEngineTypeService implements EngineTypeService {

    private final EngineTypeRepository engineTypeRepository;

    /**
     * Получить список всех типов двигателей
     *
     * @return Список типов двигателей. Пустой список, если ничего не найдено.
     */
    @Override
    public List<EngineType> findAll() {
        return engineTypeRepository.findAll();
    }

    /**
     * Найти тип двигателя по ID
     *
     * @return тип двигателя
     */
    @Override
    public Optional<EngineType> findById(int id) {
        return engineTypeRepository.findById(id);
    }

    /**
     * Сохранить тип двигателя
     *
     * @param engineType тип двигателя
     * @return тип двигателя с id
     */
    @Override
    public Optional<EngineType> add(EngineType engineType) {
        return engineTypeRepository.add(engineType);
    }

    /**
     * Обновить тип двигателя
     *
     * @param engineType тип двигателя
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(EngineType engineType) {
        return engineTypeRepository.update(engineType);
    }

    /**
     * Удалить тип двигателя
     *
     * @param engineType Тип двигателя, который нужно удалить
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(EngineType engineType) {
        return engineTypeRepository.delete(engineType);
    }

    /**
     * Удалить тип двигателя по id
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return engineTypeRepository.delete(id);
    }
}