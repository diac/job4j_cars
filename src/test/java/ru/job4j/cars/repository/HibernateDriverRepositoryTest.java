package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateDriverRepository.class,
        HibernateUserRepository.class
})
public class HibernateDriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        Driver driver = new Driver(0, value, user);
        driverRepository.add(driver);
        Driver driverInDb = driverRepository.findById(driver.getId()).orElse(new Driver());
        assertThat(driverInDb).isEqualTo(driver);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        Driver driver = new Driver(0, value, user);
        driverRepository.add(driver);
        String newName = driver.getName() + "_updated";
        driver.setName(newName);
        driverRepository.update(driver);
        Driver driverInDb = driverRepository.findById(driver.getId()).orElse(new Driver());
        assertThat(driverInDb).isEqualTo(driver);
        assertThat(driverInDb.getName()).isEqualTo(newName);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        Driver driver = new Driver(0, value, user);
        driverRepository.add(driver);
        int driverId = driver.getId();
        driverRepository.delete(driver);
        Optional<Driver> driverInDb = driverRepository.findById(driverId);
        assertThat(driverInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = new User(0, value, value, new ArrayList<>(), null);
        userRepository.add(user);
        Driver driver = new Driver(0, value, user);
        driverRepository.add(driver);
        int driverId = driver.getId();
        driverRepository.delete(driverId);
        Optional<Driver> driverInDb = driverRepository.findById(driverId);
        assertThat(driverInDb).isEmpty();
    }
}