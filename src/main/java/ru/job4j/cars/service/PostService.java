package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.dto.PostSearchParams;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();

    List<Post> findAllByUserId(int userId);

    Optional<Post> findById(int id);

    Optional<Post> findByIdWithParticipates(int id);

    Optional<Post> add(Post post);

    boolean update(Post post, User user);

    boolean update(int id, String newDescription, byte[] newPhoto, int newPrice, User user);

    boolean delete(Post post, User user);

    boolean delete(int id, User user);

    List<Post> findAllInDateRange(LocalDateTime dateFrom, LocalDateTime dateTo);

    List<Post> findAllWithPhoto();

    List<Post> findAllByCar(Car car);

    boolean deactivate(int id, User user);

    List<Post> search(PostSearchParams postSearchParams);

    boolean addParticipant(int postId, User user);

    boolean finalizeSalesOrder(int postId, int sellerId, int buyerId);
}