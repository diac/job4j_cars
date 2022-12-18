package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.enumeration.SteeringWheelSide;
import ru.job4j.cars.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        HibernateBodyStyleRepository.class,
        HibernateExteriorColorRepository.class,
        HibernateTransmissionTypeRepository.class,
        HibernateDrivetrainRepository.class,
        HibernateEngineTypeRepository.class,
        HibernateBrandRepository.class,
        HibernatePostSearchResultRepository.class
})
public class HibernatePostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BodyStyleRepository bodyStyleRepository;

    @Autowired
    private ExteriorColorRepository exteriorColorRepository;

    @Autowired
    private TransmissionTypeRepository transmissionTypeRepository;

    @Autowired
    private DrivetrainRepository drivetrainRepository;

    @Autowired
    private EngineTypeRepository engineTypeRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void whenCreate() {
        Post post = makePost();
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
        Car car1 = makeCar();
        Car car2 = makeCar();
        carRepository.add(car1);
        carRepository.add(car2);
        User user1 = new User(0, value, value, new ArrayList<>(), null);
        User user2 = new User(0, value + "_2", value + "_2", new ArrayList<>(), null);
        userRepository.add(user1);
        userRepository.add(user2);
        Post post
                = new Post(0, value, LocalDateTime.now(), user1, null, true, 0, car1, new ArrayList<>(), new HashSet<>(), null);
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
        Post post = makePost();
        postRepository.add(post);
        int postId = post.getId();
        postRepository.delete(post);
        Optional<Post> postInDb = postRepository.findById(postId);
        assertThat(postInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        Post post = makePost();
        postRepository.add(post);
        int postId = post.getId();
        postRepository.delete(postId);
        Optional<Post> postInDb = postRepository.findById(postId);
        assertThat(postInDb).isEmpty();
    }

    @Test
    public void whenFindAllInDateRange() {
        LocalDateTime midnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime twoDaysAgo = now.minusDays(2);
        String value = String.valueOf(System.currentTimeMillis());
        Car car = makeCar();
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        Post newPost
                = new Post(0, value + "_new_post", midnight, user, null, true, 0, car, new ArrayList<>(), new HashSet<>(), null);
        Post oldPost
                = new Post(0, value + "_old_post", twoDaysAgo, user, null, true, 0, car, new ArrayList<>(), new HashSet<>(), null);
        postRepository.add(newPost);
        postRepository.add(oldPost);
        List<Post> recentPosts = postRepository.findAllInDateRange(yesterday, now);
        assertThat(recentPosts.contains(newPost)).isTrue();
        assertThat(recentPosts.contains(oldPost)).isFalse();
    }

    @Test
    public void whenFindAllWithPhoto() {
        String value = String.valueOf(System.currentTimeMillis());
        Car car = makeCar();
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        Post postWithPhoto = new Post(
                0,
                value + "_with_photo",
                LocalDateTime.now(),
                user,
                new byte[]{1, 2, 3},
                true,
                0,
                car,
                new ArrayList<>(),
                new HashSet<>(),
                null

        );
        Post postWithoutPhoto = new Post(
                0,
                value + "_with_photo",
                LocalDateTime.now(),
                user,
                null,
                true,
                0,
                car,
                new ArrayList<>(),
                new HashSet<>(),
                null
        );
        postRepository.add(postWithPhoto);
        postRepository.add(postWithoutPhoto);
        List<Post> postsWithPhotos = postRepository.findAllWithPhoto();
        assertThat(postsWithPhotos.contains(postWithPhoto)).isTrue();
        assertThat(postsWithPhotos.contains(postWithoutPhoto)).isFalse();
    }

    @Test
    public void whenFindAllByCar() {
        Car car = makeCar();
        carRepository.add(car);
        Post post = makePost(car);
        postRepository.add(post);
        List<Post> posts = postRepository.findAllByCar(car);
        assertThat(posts.contains(post)).isTrue();
    }

    @Test
    public void whenFindAllByUserId() {
        Post post = makePost();
        postRepository.add(post);
        int userId = post.getUser().getId();
        List<Post> userPosts = postRepository.findAllByUserId(userId);
        assertThat(userPosts.contains(post)).isTrue();
    }

    private Car makeCar() {
        String value = String.valueOf(System.currentTimeMillis());
        BodyStyle bodyStyle = new BodyStyle(0, value);
        bodyStyleRepository.add(bodyStyle);
        ExteriorColor exteriorColor = new ExteriorColor(0, value);
        exteriorColorRepository.add(exteriorColor);
        TransmissionType transmissionType = new TransmissionType(0, value);
        transmissionTypeRepository.add(transmissionType);
        Drivetrain drivetrain = new Drivetrain(0, value);
        drivetrainRepository.add(drivetrain);
        EngineType engineType = new EngineType(0, value);
        engineTypeRepository.add(engineType);
        Brand brand = new Brand(0, value);
        brandRepository.add(brand);
        return new Car(
                0,
                bodyStyle,
                exteriorColor,
                transmissionType,
                drivetrain,
                SteeringWheelSide.LEFT,
                value,
                engineType,
                1000,
                brand,
                1000,
                2020,
                new HashSet<>()
        );
    }

    private Post makePost() {
        String value = String.valueOf(System.currentTimeMillis());
        Car car = makeCar();
        carRepository.add(car);
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        return new Post(0, value, LocalDateTime.now(), user, null, true, 0, car, new ArrayList<>(), new HashSet<>(), null);
    }

    private Post makePost(Car car) {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        return new Post(0, value, LocalDateTime.now(), user, null, true, 0, car, new ArrayList<>(), new HashSet<>(), null);
    }
}