package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateUserRepository.class
})
public class HibernateUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        User userInDb = userRepository.findById(user.getId()).orElse(new User());
        assertThat(userInDb.getId()).isEqualTo(user.getId());
        assertThat(userInDb.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        String newLogin = user.getLogin() + "_updated";
        String newPassword = user.getPassword() + "_updated";
        user.setLogin(newLogin);
        user.setPassword(newPassword);
        userRepository.update(user);
        User userInDb = userRepository.findById(user.getId()).orElse(new User());
        assertThat(userInDb.getId()).isEqualTo(user.getId());
        assertThat(userInDb.getLogin()).isEqualTo(newLogin);
        assertThat(userInDb.getPassword()).isEqualTo(newPassword);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        int userId = user.getId();
        userRepository.delete(user);
        Optional<User> userInDb = userRepository.findById(userId);
        assertThat(userInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        int userId = user.getId();
        userRepository.delete(userId);
        Optional<User> userInDb = userRepository.findById(userId);
        assertThat(userInDb).isEmpty();
    }

    @Test
    public void whenFindByLikeLogin() {
        String value = String.valueOf(System.currentTimeMillis());
        User user1 = new User(0, value + "_1", value, new ArrayList<>(), null);
        User user2 = new User(0, value + "_2", value, new ArrayList<>(), null);
        userRepository.add(user1);
        userRepository.add(user2);
        List<Integer> userIds = userRepository.findByLikeLogin(value).stream()
                .map(user -> user.getId())
                .toList();
        assertThat(userIds.containsAll(List.of(user1.getId(), user2.getId()))).isTrue();
    }

    @Test
    public void whenFindByLogin() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        Optional<User> userInDb = userRepository.findByLogin(value);
        assertThat(userInDb).isNotEmpty();
        assertThat(userInDb.get().getId()).isEqualTo(user.getId());
    }
}