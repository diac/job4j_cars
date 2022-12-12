package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.ExteriorColor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateExteriorColorRepository.class
})
public class HibernateExteriorColorRepositoryTest {

    @Autowired
    private ExteriorColorRepository exteriorColorRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        ExteriorColor exteriorColor = new ExteriorColor(0, value);
        exteriorColorRepository.add(exteriorColor);
        ExteriorColor exteriorColorInDb = exteriorColorRepository.findById(exteriorColor.getId())
                .orElse(new ExteriorColor());
        assertThat(exteriorColorInDb).isEqualTo(exteriorColor);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        ExteriorColor exteriorColor = new ExteriorColor(0, value);
        exteriorColorRepository.add(exteriorColor);
        List<ExteriorColor> exteriorColors = exteriorColorRepository.findAll();
        assertThat(exteriorColors.contains(exteriorColor)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        ExteriorColor exteriorColor = new ExteriorColor(0, value);
        exteriorColorRepository.add(exteriorColor);
        exteriorColor.setName(exteriorColor.getName() + "_updated");
        exteriorColorRepository.update(exteriorColor);
        ExteriorColor exteriorColorInDb = exteriorColorRepository.findById(exteriorColor.getId())
                .orElse(new ExteriorColor());
        assertThat(exteriorColorInDb).isEqualTo(exteriorColor);
        assertThat(exteriorColorInDb.getName()).isEqualTo(exteriorColor.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        ExteriorColor exteriorColor = new ExteriorColor(0, value);
        exteriorColorRepository.add(exteriorColor);
        int exteriorColorId = exteriorColor.getId();
        exteriorColorRepository.delete(exteriorColor);
        Optional<ExteriorColor> exteriorColorInDb = exteriorColorRepository.findById(exteriorColorId);
        assertThat(exteriorColorInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        ExteriorColor exteriorColor = new ExteriorColor(0, value);
        exteriorColorRepository.add(exteriorColor);
        int exteriorColorId = exteriorColor.getId();
        exteriorColorRepository.delete(exteriorColorId);
        Optional<ExteriorColor> exteriorColorInDb = exteriorColorRepository.findById(exteriorColorId);
        assertThat(exteriorColorInDb).isEmpty();
    }
}