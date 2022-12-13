package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.EngineType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateEngineTypeRepository.class
})
public class HibernateEngineTypeRepositoryTest {

    @Autowired
    private EngineTypeRepository engineTypeRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineType engineType = new EngineType(0, value);
        engineTypeRepository.add(engineType);
        EngineType engineTypeInDb = engineTypeRepository.findById(engineType.getId())
                .orElse(new EngineType());
        assertThat(engineTypeInDb).isEqualTo(engineType);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineType engineType = new EngineType(0, value);
        engineTypeRepository.add(engineType);
        List<EngineType> engineTypes = engineTypeRepository.findAll();
        assertThat(engineTypes.contains(engineType)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineType engineType = new EngineType(0, value);
        engineTypeRepository.add(engineType);
        engineType.setName(engineType.getName() + "_updated");
        boolean success = engineTypeRepository.update(engineType);
        EngineType engineTypeInDb = engineTypeRepository.findById(engineType.getId())
                .orElse(new EngineType());
        assertThat(success).isTrue();
        assertThat(engineTypeInDb).isEqualTo(engineType);
        assertThat(engineTypeInDb.getName()).isEqualTo(engineType.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineType engineType = new EngineType(0, value);
        engineTypeRepository.add(engineType);
        int engineTypeId = engineType.getId();
        engineTypeRepository.delete(engineType);
        Optional<EngineType> engineTypeInDb = engineTypeRepository.findById(engineTypeId);
        assertThat(engineTypeInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineType engineType = new EngineType(0, value);
        engineTypeRepository.add(engineType);
        int engineTypeId = engineType.getId();
        engineTypeRepository.delete(engineTypeId);
        Optional<EngineType> engineTypeInDb = engineTypeRepository.findById(engineTypeId);
        assertThat(engineTypeInDb).isEmpty();
    }
}