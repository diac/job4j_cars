package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.EngineVolume;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateEngineVolumeRepository.class
})
public class HibernateEngineVolumeRepositoryTest {

    @Autowired
    private EngineVolumeRepository engineVolumeRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineVolume engineVolume = new EngineVolume(0, value);
        engineVolumeRepository.add(engineVolume);
        EngineVolume engineVolumeInDb = engineVolumeRepository.findById(engineVolume.getId())
                .orElse(new EngineVolume());
        assertThat(engineVolumeInDb).isEqualTo(engineVolume);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineVolume engineVolume = new EngineVolume(0, value);
        engineVolumeRepository.add(engineVolume);
        List<EngineVolume> engineVolumes = engineVolumeRepository.findAll();
        assertThat(engineVolumes.contains(engineVolume));
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineVolume engineVolume = new EngineVolume(0, value);
        engineVolumeRepository.add(engineVolume);
        engineVolume.setName(engineVolume.getName() + "_updated");
        boolean success = engineVolumeRepository.update(engineVolume);
        EngineVolume engineVolumeInDb = engineVolumeRepository.findById(engineVolume.getId())
                .orElse(new EngineVolume());
        assertThat(success).isTrue();
        assertThat(engineVolumeInDb).isEqualTo(engineVolume);
        assertThat(engineVolumeInDb.getName()).isEqualTo(engineVolume.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineVolume engineVolume = new EngineVolume(0, value);
        engineVolumeRepository.add(engineVolume);
        int engineVolumeId = engineVolume.getId();
        engineVolumeRepository.delete(engineVolume);
        Optional<EngineVolume> engineVolumeInDb = engineVolumeRepository.findById(engineVolumeId);
        assertThat(engineVolumeInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        EngineVolume engineVolume = new EngineVolume(0, value);
        engineVolumeRepository.add(engineVolume);
        int engineVolumeId = engineVolume.getId();
        engineVolumeRepository.delete(engineVolumeId);
        Optional<EngineVolume> engineVolumeInDb = engineVolumeRepository.findById(engineVolumeId);
        assertThat(engineVolumeInDb).isEmpty();
    }
}