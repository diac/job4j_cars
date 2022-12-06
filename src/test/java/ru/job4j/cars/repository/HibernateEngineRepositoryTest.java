package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.Engine;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateEngineRepository.class
})
public class HibernateEngineRepositoryTest {

    @Autowired
    private EngineRepository repository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        repository.add(engine);
        Engine engineInDb = repository.findById(engine.getId()).orElse(new Engine());
        assertThat(engineInDb).isEqualTo(engine);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        repository.add(engine);
        engine.setName(engine.getName() + "_updated");
        repository.update(engine);
        Engine engineInDb = repository.findById(engine.getId()).orElse(new Engine());
        assertThat(engineInDb).isEqualTo(engine);
        assertThat(engineInDb.getName()).isEqualTo(engine.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        repository.add(engine);
        int engineId = engine.getId();
        repository.delete(engine);
        Optional<Engine> engineInDb = repository.findById(engineId);
        assertThat(engineInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        Engine engine = new Engine(0, value);
        repository.add(engine);
        int engineId = engine.getId();
        repository.delete(engineId);
        Optional<Engine> engineInDb = repository.findById(engineId);
        assertThat(engineInDb).isEmpty();
    }
}