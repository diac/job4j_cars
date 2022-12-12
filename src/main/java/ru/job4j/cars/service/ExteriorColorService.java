package ru.job4j.cars.service;

import ru.job4j.cars.model.ExteriorColor;

import java.util.List;
import java.util.Optional;

public interface ExteriorColorService {

    List<ExteriorColor> findAll();

    Optional<ExteriorColor> findById(int id);

    Optional<ExteriorColor> add(ExteriorColor exteriorColor);

    boolean update(ExteriorColor exteriorColor);

    boolean delete(ExteriorColor exteriorColor);

    boolean delete(int id);
}