package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.BrandRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Brand в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleBrandService implements BrandService {

    private final BrandRepository brandRepository;

    /**
     * Получить список всех марок
     *
     * @return Список марок. Пустой список, если ничего не найдено.
     */
    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    /**
     * Найти марку по ID
     *
     * @return Марка
     */
    @Override
    public Optional<Brand> findById(int id) {
        return brandRepository.findById(id);
    }

    /**
     * Сохранить марку
     *
     * @param brand Марка
     * @return Марка с id
     */
    @Override
    public Optional<Brand> add(Brand brand) {
        return brandRepository.add(brand);
    }

    /**
     * Обновить марку
     *
     * @param brand Марка
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(Brand brand) {
        return brandRepository.update(brand);
    }

    /**
     * Удалить марку
     *
     * @param brand Марка, которую нужно удалить
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Brand brand) {
        return brandRepository.delete(brand);
    }

    /**
     * Удалить маоку по id
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return brandRepository.delete(id);
    }
}