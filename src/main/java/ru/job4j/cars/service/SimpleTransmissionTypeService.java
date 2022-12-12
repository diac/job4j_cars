package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.TransmissionType;
import ru.job4j.cars.repository.TransmissionTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели BodyStyle в репозитории
 */
@Service
@AllArgsConstructor
public class SimpleTransmissionTypeService implements TransmissionTypeService {

    private final TransmissionTypeRepository transmissionTypeRepository;

    /**
     * Получить список всех типов коробок передач
     *
     * @return Список типов коробок передач. Пустой список, если ничего не найдено.
     */
    @Override
    public List<TransmissionType> findAll() {
        return transmissionTypeRepository.findAll();
    }

    /**
     * Найти тип коробки передач по ID
     *
     * @return тип коробки передач
     */
    @Override
    public Optional<TransmissionType> findById(int id) {
        return transmissionTypeRepository.findById(id);
    }

    /**
     * Сохранить тип коробки передач
     *
     * @param transmissionType тип коробки передач
     * @return тип коробки передач с id
     */
    @Override
    public Optional<TransmissionType> add(TransmissionType transmissionType) {
        return transmissionTypeRepository.add(transmissionType);
    }

    /**
     * Обновить тип коробки передач
     *
     * @param transmissionType тип коробки передач
     * @return true в случае удачного обновления. Иначе -- false
     */
    @Override
    public boolean update(TransmissionType transmissionType) {
        return transmissionTypeRepository.update(transmissionType);
    }

    /**
     * Удалить тип коробки передач
     *
     * @param transmissionType Тип коробки передач, который нужно удалить
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(TransmissionType transmissionType) {
        return transmissionTypeRepository.delete(transmissionType);
    }

    /**
     * Удалить тип коробки передач по id
     *
     * @param id ID
     * @return true в случае удачного удаления. Иначе -- false
     */
    @Override
    public boolean delete(int id) {
        return transmissionTypeRepository.delete(id);
    }
}