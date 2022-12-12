package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.BodyStyle;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateBodyStyleRepository.class
})
public class HibernateBodyStyleRepositoryTest {

    @Autowired
    private BodyStyleRepository bodyStyleRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        BodyStyle bodyStyle = new BodyStyle(0, value);
        bodyStyleRepository.add(bodyStyle);
        BodyStyle bodyStyleInDb = bodyStyleRepository.findById(bodyStyle.getId())
                .orElse(new BodyStyle());
        assertThat(bodyStyleInDb).isEqualTo(bodyStyle);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        BodyStyle bodyStyle = new BodyStyle(0, value);
        bodyStyleRepository.add(bodyStyle);
        List<BodyStyle> bodyStyles = bodyStyleRepository.findAll();
        assertThat(bodyStyles.contains(bodyStyle)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        BodyStyle bodyStyle = new BodyStyle(0, value);
        bodyStyleRepository.add(bodyStyle);
        bodyStyle.setName(bodyStyle.getName() + "_updated");
        bodyStyleRepository.update(bodyStyle);
        BodyStyle bodyStyleInDb = bodyStyleRepository.findById(bodyStyle.getId())
                .orElse(new BodyStyle());
        assertThat(bodyStyleInDb).isEqualTo(bodyStyle);
        assertThat(bodyStyleInDb.getName()).isEqualTo(bodyStyle.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        BodyStyle bodyStyle = new BodyStyle(0, value);
        bodyStyleRepository.add(bodyStyle);
        int bodyStyleId = bodyStyle.getId();
        bodyStyleRepository.delete(bodyStyle);
        Optional<BodyStyle> bodyStyleInDb = bodyStyleRepository.findById(bodyStyleId);
        assertThat(bodyStyleInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        BodyStyle bodyStyle = new BodyStyle(0, value);
        bodyStyleRepository.add(bodyStyle);
        int bodyStyleId = bodyStyle.getId();
        bodyStyleRepository.delete(bodyStyleId);
        Optional<BodyStyle> bodyStyleInDb = bodyStyleRepository.findById(bodyStyleId);
        assertThat(bodyStyleInDb).isEmpty();
    }
}