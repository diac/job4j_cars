package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernatePostRepository.class,
        HibernateCarRepository.class,
        HibernateUserRepository.class,
        HibernateEngineRepository.class
})
public class HibernatePostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EngineRepository engineRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>());
        userRepository.add(user);
        Post post = new Post(0, value, LocalDateTime.now(), user, null, car, new ArrayList<>());
        postRepository.add(post);
        Post postInDb = postRepository.findById(post.getId()).orElse(new Post());
        assertThat(postInDb).isEqualTo(post);
        assertThat(postInDb.getDescription()).isEqualTo(post.getDescription());
        assertThat(postInDb.getCar()).isEqualTo(post.getCar());
        assertThat(postInDb.getUser().getId()).isEqualTo(post.getUser().getId());
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car1 = new Car(0, value, engine, new HashSet<>());
        Car car2 = new Car(0, value + "_2", engine, new HashSet<>());
        carRepository.add(car1);
        carRepository.add(car2);
        User user1 = new User(0, value, value, new ArrayList<>());
        User user2 = new User(0, value + "_2", value + "_2", new ArrayList<>());
        userRepository.add(user1);
        userRepository.add(user2);
        Post post = new Post(0, value, LocalDateTime.now(), user1, null, car1, new ArrayList<>());
        postRepository.add(post);
        String newDescription = post.getDescription() + "_updated";
        post.setDescription(newDescription);
        post.setCar(car2);
        post.setUser(user2);
        postRepository.update(post);
        Post postInDb = postRepository.findById(post.getId()).orElse(new Post());
        assertThat(postInDb).isEqualTo(post);
        assertThat(postInDb.getDescription()).isEqualTo(newDescription);
        assertThat(postInDb.getCar()).isEqualTo(car2);
        assertThat(postInDb.getUser().getId()).isEqualTo(user2.getId());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>());
        userRepository.add(user);
        Post post = new Post(0, value, LocalDateTime.now(), user, null, car, new ArrayList<>());
        postRepository.add(post);
        int postId = post.getId();
        postRepository.delete(post);
        Optional<Post> postInDb = postRepository.findById(postId);
        assertThat(postInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>());
        userRepository.add(user);
        Post post = new Post(0, value, LocalDateTime.now(), user, null, car, new ArrayList<>());
        postRepository.add(post);
        int postId = post.getId();
        postRepository.delete(postId);
        Optional<Post> postInDb = postRepository.findById(postId);
        assertThat(postInDb).isEmpty();
    }

    @Test
    public void whenFindAllRecent() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoDaysAgo = now.minusDays(2);
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>());
        userRepository.add(user);
        Post newPost = new Post(0, value + "_new_post", now, user, null, car, new ArrayList<>());
        Post oldPost = new Post(0, value + "_old_post", twoDaysAgo, user, null, car, new ArrayList<>());
        postRepository.add(newPost);
        postRepository.add(oldPost);
        List<Post> recentPosts = postRepository.findAllRecent();
        assertThat(recentPosts.contains(newPost)).isTrue();
        assertThat(recentPosts.contains(oldPost)).isFalse();
    }

    @Test
    public void whenFindAllWithPhoto() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>());
        userRepository.add(user);
        Post postWithPhoto = new Post(
                0,
                value + "_with_photo",
                LocalDateTime.now(),
                user,
                new byte[] {1, 2, 3},
                car,
                new ArrayList<>()
        );
        Post postWithoutPhoto = new Post(
                0,
                value + "_with_photo",
                LocalDateTime.now(),
                user,
                null,
                car,
                new ArrayList<>()
        );
        postRepository.add(postWithPhoto);
        postRepository.add(postWithoutPhoto);
        List<Post> postsWithPhotos = postRepository.findAllWithPhoto();
        assertThat(postsWithPhotos.contains(postWithPhoto)).isTrue();
        assertThat(postsWithPhotos.contains(postWithoutPhoto)).isFalse();
    }

    @Test
    public void whenFindAllByCar() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>());
        userRepository.add(user);
        Post post = new Post(0, value, LocalDateTime.now(), user, null, car, new ArrayList<>());
        postRepository.add(post);
        List<Post> posts = postRepository.findAllByCar(car);
        assertThat(posts.contains(post)).isTrue();
    }
}