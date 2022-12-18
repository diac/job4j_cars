package ru.job4j.cars.repository;

import ru.job4j.cars.model.SalesOrder;

import java.util.List;
import java.util.Optional;

public interface SalesOrderRepository {

    List<SalesOrder> findAll();

    Optional<SalesOrder> findById(int id);

    Optional<SalesOrder> add(SalesOrder salesOrder);

    boolean update(SalesOrder salesOrder);

    boolean delete(SalesOrder salesOrder);

    boolean delete(int id);
}