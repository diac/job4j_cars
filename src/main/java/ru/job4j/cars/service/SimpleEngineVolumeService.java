package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.EngineVolume;
import ru.job4j.cars.repository.EngineVolumeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели EngineVolume в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleEngineVolumeService implements EngineVolumeService {

    private final EngineVolumeRepository engineVolumeRepository;

    /**
     * Получить список всех объемов двигателей
     *
     * @return Список объемов двигателей. Пустой список, если ничего не найдено.
     */
    @Override
    public List<EngineVolume> findAll() {
        return engineVolumeRepository.findAll();
    }

    /**
     * Найти объем двигателя по ID
     *
     * @return Объем двигателя
     */
    @Override
    public Optional<EngineVolume> findById(int id) {
        return engineVolumeRepository.findById(id);
    }

    /**
     * Сохранить объем двигателя
     *
     * @param engineVolume объем двигателя
     * @return Объем двигателя с id
     */
    @Override
    public Optional<EngineVolume> add(EngineVolume engineVolume) {
        return engineVolumeRepository.add(engineVolume);
    }

    /**
     * Обновить объем двигателя
     *
     * @param engineVolume объем двигателя
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(EngineVolume engineVolume) {
        return engineVolumeRepository.update(engineVolume);
    }

    /**
     * Удалить объем двигателя
     *
     * @param engineVolume Объем двигателя, который нужно удалить
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(EngineVolume engineVolume) {
        return engineVolumeRepository.delete(engineVolume);
    }

    /**
     * Удалить объем двигателя по id
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return engineVolumeRepository.delete(id);
    }
}