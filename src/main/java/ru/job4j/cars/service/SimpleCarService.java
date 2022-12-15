package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Car в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {

    private final CarRepository carRepository;

    /**
     * Получить список всех автомобилей
     *
     * @return Список автомобилей. Пустой список, если ничего не найдено.
     */
    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    /**
     * Найти автомобиль по ID
     *
     * @return Автомобиль
     */
    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    /**
     * Сохранить автомобиль
     *
     * @param car Автомобиль
     * @return Автомобиль с id
     */
    @Override
    public Optional<Car> add(Car car) {
        return carRepository.add(car);
    }

    /**
     * Обновить автомобиль
     *
     * @param car Автомобиль
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    /**
     * Удалить автомобиль
     *
     * @param car Автомобиль
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Car car) {
        return carRepository.delete(car);
    }

    /**
     * Удалить автомобиль по ID
     *
     * @param id ID автомобиля
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return carRepository.delete(id);
    }
}