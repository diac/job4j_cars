package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    List<Post> findAll();

    Optional<Post> findById(int id);

    Optional<Post> add(Post post);

    boolean update(Post post);

    boolean delete(Post post);

    boolean delete(int id);
}