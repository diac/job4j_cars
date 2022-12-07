package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> add(User user);

    boolean update(User user);

    boolean delete(User user);

    boolean delete(int id);

    List<User> findAllOrderById();

    List<User> findByLikeLogin(String key);

    Optional<User> findByLogin(String login);
}