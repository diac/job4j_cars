package ru.job4j.cars.service;

import ru.job4j.cars.model.TransmissionType;

import java.util.List;
import java.util.Optional;

public interface TransmissionTypeService {

    List<TransmissionType> findAll();

    Optional<TransmissionType> findById(int id);

    Optional<TransmissionType> add(TransmissionType transmissionType);

    boolean update(TransmissionType transmissionType);

    boolean delete(TransmissionType transmissionType);

    boolean delete(int id);
}