package ru.job4j.cars.repository;

import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverRepository {

    List<Driver> findAll();

    Optional<Driver> findById(int id);

    Optional<Driver> add(Driver driver);

    boolean update(Driver driver);

    boolean delete(Driver driver);

    boolean delete(int id);
}