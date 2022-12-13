package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateBrandRepository.class
})
public class HibernateBrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        Brand brand = new Brand(0, value);
        brandRepository.add(brand);
        Brand brandInDb = brandRepository.findById(brand.getId()).orElse(new Brand());
        assertThat(brandInDb).isEqualTo(brand);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Brand brand = new Brand(0, value);
        brandRepository.add(brand);
        List<Brand> brands = brandRepository.findAll();
        assertThat(brands.contains(brand)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Brand brand = new Brand(0, value);
        brandRepository.add(brand);
        brand.setName(brand.getName() + "_updated");
        boolean success = brandRepository.update(brand);
        Brand brandInDb = brandRepository.findById(brand.getId()).orElse(new Brand());
        assertThat(success).isTrue();
        assertThat(brandInDb).isEqualTo(brand);
        assertThat(brandInDb.getName()).isEqualTo(brand.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Brand brand = new Brand(0, value);
        brandRepository.add(brand);
        int brandId = brand.getId();
        brandRepository.delete(brand);
        Optional<Brand> brandInDb = brandRepository.findById(brandId);
        assertThat(brandInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        Brand brand = new Brand(0, value);
        brandRepository.add(brand);
        int brandId = brand.getId();
        brandRepository.delete(brandId);
        Optional<Brand> brandInDb = brandRepository.findById(brandId);
        assertThat(brandInDb).isEmpty();
    }
}