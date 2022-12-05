package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineRepository {

    List<Engine> findAll();

    Optional<Engine> findById(int id);

    Optional<Engine> add(Engine engine);

    boolean update(Engine engine);

    boolean delete(Engine engine);

    boolean delete(int id);
}