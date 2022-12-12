package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Drivetrain;
import ru.job4j.cars.repository.DrivetrainRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Drivetrain в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleDrivetrainService implements DrivetrainService {

    private final DrivetrainRepository drivetrainRepository;

    /**
     * Получить список всех типов приводов
     *
     * @return Список типов приводов. Пустой список, если ничего не найдено.
     */
    @Override
    public List<Drivetrain> findAll() {
        return drivetrainRepository.findAll();
    }

    /**
     * Найти тип привода по ID
     *
     * @return тип привода
     */
    @Override
    public Optional<Drivetrain> findById(int id) {
        return drivetrainRepository.findById(id);
    }

    /**
     * Сохранить тип привода
     *
     * @param drivetrain тип привода
     * @return тип привода с id
     */
    @Override
    public Optional<Drivetrain> add(Drivetrain drivetrain) {
        return drivetrainRepository.add(drivetrain);
    }

    /**
     * Обновить тип привода
     *
     * @param drivetrain тип привода
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(Drivetrain drivetrain) {
        return drivetrainRepository.update(drivetrain);
    }

    /**
     * Удалить тип привода
     *
     * @param drivetrain Тип привода, который нужно удалить
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Drivetrain drivetrain) {
        return drivetrainRepository.delete(drivetrain);
    }

    /**
     * Удалить тип привода по id
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return drivetrainRepository.delete(id);
    }
}