package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();

    Optional<Post> findById(int id);

    Optional<Post> add(Post post);

    boolean update(Post post, User user);

    boolean delete(Post post, User user);

    boolean delete(int id, User user);

    List<Post> findAllInDateRange(LocalDateTime dateFrom, LocalDateTime dateTo);

    List<Post> findAllWithPhoto();

    List<Post> findAllByCar(Car car);
}