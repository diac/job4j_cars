package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.Drivetrain;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateDrivetrainRepository.class
})
public class HibernateDrivetrainRepositoryTest {

    @Autowired
    private DrivetrainRepository drivetrainRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        Drivetrain drivetrain = new Drivetrain(0, value);
        drivetrainRepository.add(drivetrain);
        Drivetrain drivetrainInDb = drivetrainRepository.findById(drivetrain.getId())
                .orElse(new Drivetrain());
        assertThat(drivetrainInDb).isEqualTo(drivetrain);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Drivetrain drivetrain = new Drivetrain(0, value);
        drivetrainRepository.add(drivetrain);
        List<Drivetrain> drivetrains = drivetrainRepository.findAll();
        assertThat(drivetrains.contains(drivetrain)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Drivetrain drivetrain = new Drivetrain(0, value);
        drivetrainRepository.add(drivetrain);
        drivetrain.setName(drivetrain.getName() + "_updated");
        boolean success = drivetrainRepository.update(drivetrain);
        Drivetrain drivetrainInDb = drivetrainRepository.findById(drivetrain.getId())
                .orElse(new Drivetrain());
        assertThat(success).isTrue();
        assertThat(drivetrainInDb).isEqualTo(drivetrain);
        assertThat(drivetrainInDb.getName()).isEqualTo(drivetrain.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Drivetrain drivetrain = new Drivetrain(0, value);
        drivetrainRepository.add(drivetrain);
        int drivetrainId = drivetrain.getId();
        drivetrainRepository.delete(drivetrain);
        Optional<Drivetrain> drivetrainInDb = drivetrainRepository.findById(drivetrainId);
        assertThat(drivetrainInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        Drivetrain drivetrain = new Drivetrain(0, value);
        drivetrainRepository.add(drivetrain);
        int drivetrainId = drivetrain.getId();
        drivetrainRepository.delete(drivetrainId);
        Optional<Drivetrain> drivetrainInDb = drivetrainRepository.findById(drivetrainId);
        assertThat(drivetrainInDb).isEmpty();
    }
}