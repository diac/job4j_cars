package ru.job4j.cars.repository;

import ru.job4j.cars.model.ExteriorColor;

import java.util.List;
import java.util.Optional;

public interface ExteriorColorRepository {

    List<ExteriorColor> findAll();

    Optional<ExteriorColor> findById(int id);

    Optional<ExteriorColor> add(ExteriorColor exteriorColor);

    boolean update(ExteriorColor exteriorColor);

    boolean delete(ExteriorColor exteriorColor);

    boolean delete(int id);
}