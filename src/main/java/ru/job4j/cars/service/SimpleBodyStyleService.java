package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.BodyStyle;
import ru.job4j.cars.repository.BodyStyleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBodyStyleService implements BodyStyleService {

    private final BodyStyleRepository bodyStyleRepository;

    /**
     * Получить список всех типов кузовов
     *
     * @return Список типов кузовов. Пустой список, если ничего не найдено.
     */
    @Override
    public List<BodyStyle> findAll() {
        return bodyStyleRepository.findAll();
    }

    /**
     * Найти тип кузова по ID
     *
     * @return тип кузова.
     */
    @Override
    public Optional<BodyStyle> findById(int id) {
        return bodyStyleRepository.findById(id);
    }

    /**
     * Сохранить тип кузова
     *
     * @param bodyStyle тип кузова
     * @return пользователь с id
     */
    @Override
    public Optional<BodyStyle> add(BodyStyle bodyStyle) {
        return bodyStyleRepository.add(bodyStyle);
    }

    /**
     * Обновить тип кузова
     *
     * @param bodyStyle тип кузова
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(BodyStyle bodyStyle) {
        return bodyStyleRepository.update(bodyStyle);
    }

    /**
     * Удалить тип кузова
     *
     * @param bodyStyle Тип кузова, который нужно удалить
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(BodyStyle bodyStyle) {
        return bodyStyleRepository.delete(bodyStyle);
    }

    /**
     * Удалить тип кузова по id
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return bodyStyleRepository.delete(id);
    }
}