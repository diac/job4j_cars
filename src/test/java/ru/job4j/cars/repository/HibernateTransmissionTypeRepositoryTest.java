package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.TransmissionType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateTransmissionTypeRepository.class
})
public class HibernateTransmissionTypeRepositoryTest {

    @Autowired
    private TransmissionTypeRepository transmissionTypeRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        TransmissionType transmissionType = new TransmissionType(0, value);
        transmissionTypeRepository.add(transmissionType);
        TransmissionType transmissionTypeInDb = transmissionTypeRepository.findById(transmissionType.getId())
                .orElse(new TransmissionType());
        assertThat(transmissionTypeInDb).isEqualTo(transmissionType);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        TransmissionType transmissionType = new TransmissionType(0, value);
        transmissionTypeRepository.add(transmissionType);
        List<TransmissionType> transmissionTypes = transmissionTypeRepository.findAll();
        assertThat(transmissionTypes.contains(transmissionType)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        TransmissionType transmissionType = new TransmissionType(0, value);
        transmissionTypeRepository.add(transmissionType);
        transmissionType.setName(transmissionType.getName() + "_updated");
        transmissionTypeRepository.update(transmissionType);
        TransmissionType transmissionTypeInDb = transmissionTypeRepository.findById(transmissionType.getId())
                .orElse(new TransmissionType());
        assertThat(transmissionTypeInDb).isEqualTo(transmissionType);
        assertThat(transmissionTypeInDb.getName()).isEqualTo(transmissionType.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        TransmissionType transmissionType = new TransmissionType(0, value);
        transmissionTypeRepository.add(transmissionType);
        int transmissionTypeId = transmissionType.getId();
        transmissionTypeRepository.delete(transmissionType);
        Optional<TransmissionType> transmissionTypeInDb = transmissionTypeRepository.findById(transmissionTypeId);
        assertThat(transmissionTypeInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        TransmissionType transmissionType = new TransmissionType(0, value);
        transmissionTypeRepository.add(transmissionType);
        int transmissionTypeId = transmissionType.getId();
        transmissionTypeRepository.delete(transmissionTypeId);
        Optional<TransmissionType> transmissionTypeInDb = transmissionTypeRepository.findById(transmissionTypeId);
        assertThat(transmissionTypeInDb).isEmpty();
    }
}