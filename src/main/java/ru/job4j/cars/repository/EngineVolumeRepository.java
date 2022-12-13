package ru.job4j.cars.repository;

import ru.job4j.cars.model.EngineVolume;

import java.util.List;
import java.util.Optional;

public interface EngineVolumeRepository {

    List<EngineVolume> findAll();

    Optional<EngineVolume> findById(int id);

    Optional<EngineVolume> add(EngineVolume engineVolume);

    boolean update(EngineVolume engineVolume);

    boolean delete(EngineVolume engineVolume);

    boolean delete(int id);
}