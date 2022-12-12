package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.ExteriorColor;
import ru.job4j.cars.repository.ExteriorColorRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели ExteriorColor в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleExteriorColorService implements ExteriorColorService {

    private final ExteriorColorRepository exteriorColorRepository;

    /**
     * Получить список всех цветов кузовов
     *
     * @return Список цветов кузовов. Пустой список, если ничего не найдено.
     */
    @Override
    public List<ExteriorColor> findAll() {
        return exteriorColorRepository.findAll();
    }

    /**
     * Найти цвет кузова по ID
     *
     * @return тип кузова.
     */
    @Override
    public Optional<ExteriorColor> findById(int id) {
        return exteriorColorRepository.findById(id);
    }

    /**
     * Сохранить цвет кузова
     *
     * @param exteriorColor цвет кузова
     * @return пользователь с id
     */
    @Override
    public Optional<ExteriorColor> add(ExteriorColor exteriorColor) {
        return exteriorColorRepository.add(exteriorColor);
    }

    /**
     * Обновить цвет кузова
     *
     * @param exteriorColor цвет кузова
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(ExteriorColor exteriorColor) {
        return exteriorColorRepository.update(exteriorColor);
    }

    /**
     * Удалить цвет кузова
     *
     * @param exteriorColor Цвет кузова, который нужно удалить
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(ExteriorColor exteriorColor) {
        return exteriorColorRepository.delete(exteriorColor);
    }

    /**
     * Удалить цвет кузова по id
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return exteriorColorRepository.delete(id);
    }
}