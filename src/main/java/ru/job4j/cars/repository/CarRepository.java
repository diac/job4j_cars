package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> findAll();

    Optional<Car> findById(int id);

    Optional<Car> add(Car car);

    boolean update(Car car);

    boolean delete(Car car);

    boolean delete(int id);
}