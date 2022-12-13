package ru.job4j.cars.repository;

import ru.job4j.cars.model.EngineType;

import java.util.List;
import java.util.Optional;

public interface EngineTypeRepository {

    List<EngineType> findAll();

    Optional<EngineType> findById(int id);

    Optional<EngineType> add(EngineType engineType);

    boolean update(EngineType engineType);

    boolean delete(EngineType engineType);

    boolean delete(int id);
}