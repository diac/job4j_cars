package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateCarRepository.class,
        HibernateEngineRepository.class
})
public class HibernateCarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private EngineRepository engineRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        Car carInDb = carRepository.findById(car.getId())
                .orElse(new Car());
        assertThat(carInDb).isEqualTo(car);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine1 = new Engine(0, value);
        Engine engine2 = new Engine(0, value + "_2");
        engineRepository.add(engine1);
        engineRepository.add(engine2);
        Car car = new Car(0, value, engine1, new HashSet<>());
        carRepository.add(car);
        car.setName(car.getName() + "_updated");
        car.setEngine(engine2);
        carRepository.update(car);
        Car carInDb = carRepository.findById(car.getId()).orElse(new Car());
        assertThat(carInDb).isEqualTo(car);
        assertThat(carInDb.getName()).isEqualTo(car.getName());
        assertThat(carInDb.getEngine()).isEqualTo(car.getEngine());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        int carId = car.getId();
        carRepository.delete(car);
        Optional<Car> carInDb = carRepository.findById(carId);
        assertThat(carInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        engineRepository.add(engine);
        Car car = new Car(0, value, engine, new HashSet<>());
        carRepository.add(car);
        int carId = car.getId();
        carRepository.delete(carId);
        Optional<Car> carInDb = carRepository.findById(carId);
        assertThat(carInDb).isEmpty();
    }
}