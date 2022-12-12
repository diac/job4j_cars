package ru.job4j.cars.repository;

import ru.job4j.cars.model.Drivetrain;

import java.util.List;
import java.util.Optional;

public interface DrivetrainRepository {

    List<Drivetrain> findAll();

    Optional<Drivetrain> findById(int id);

    Optional<Drivetrain> add(Drivetrain drivetrain);

    boolean update(Drivetrain drivetrain);

    boolean delete(Drivetrain drivetrain);

    boolean delete(int id);
}