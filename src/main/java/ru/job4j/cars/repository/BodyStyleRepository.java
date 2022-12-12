package ru.job4j.cars.repository;

import ru.job4j.cars.model.BodyStyle;

import java.util.List;
import java.util.Optional;

public interface BodyStyleRepository {

    List<BodyStyle> findAll();

    Optional<BodyStyle> findById(int id);

    Optional<BodyStyle> add(BodyStyle bodyStyle);

    boolean update(BodyStyle bodyStyle);

    boolean delete(BodyStyle bodyStyle);

    boolean delete(int id);
}