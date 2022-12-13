package ru.job4j.cars.service;

import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    List<Brand> findAll();

    Optional<Brand> findById(int id);

    Optional<Brand> add(Brand brand);

    boolean update(Brand brand);

    boolean delete(Brand brand);

    boolean delete(int id);
}