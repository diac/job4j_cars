package ru.job4j.cars.service;

import ru.job4j.cars.model.Drivetrain;

import java.util.List;
import java.util.Optional;

public interface DrivetrainService {

    List<Drivetrain> findAll();

    Optional<Drivetrain> findById(int id);

    Optional<Drivetrain> add(Drivetrain drivetrain);

    boolean update(Drivetrain drivetrain);

    boolean delete(Drivetrain drivetrain);

    boolean delete(int id);
}