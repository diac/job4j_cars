package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.enumeration.SteeringWheelSide;
import ru.job4j.cars.model.*;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernateCarRepository.class,
        HibernateBodyStyleRepository.class,
        HibernateExteriorColorRepository.class,
        HibernateTransmissionTypeRepository.class,
        HibernateDrivetrainRepository.class,
        HibernateEngineTypeRepository.class,
        HibernateEngineVolumeRepository.class,
        HibernateBrandRepository.class
})
public class HibernateCarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BodyStyleRepository bodyStyleRepository;

    @Autowired
    private ExteriorColorRepository exteriorColorRepository;

    @Autowired
    private TransmissionTypeRepository transmissionTypeRepository;

    @Autowired
    private DrivetrainRepository drivetrainRepository;

    @Autowired
    private EngineTypeRepository engineTypeRepository;

    @Autowired
    private EngineVolumeRepository engineVolumeRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void whenCreate() {
        Car car = makeCar();
        carRepository.add(car);
        Car carInDb = carRepository.findById(car.getId())
                .orElse(new Car());
        assertThat(carInDb).isEqualTo(car);
    }

    @Test
    public void whenUpdate() {
        Car carInitial = makeCar();
        Car carDifferent = makeCar();
        carRepository.add(carInitial);
        carInitial.setBodyStyle(carDifferent.getBodyStyle());
        carInitial.setExteriorColor(carDifferent.getExteriorColor());
        carInitial.setTransmissionType(carDifferent.getTransmissionType());
        carInitial.setDrivetrain(carDifferent.getDrivetrain());
        carInitial.setSteeringWheelSide(SteeringWheelSide.RIGHT);
        carInitial.setModelName(carDifferent.getModelName());
        carInitial.setEngineType(carDifferent.getEngineType());
        carInitial.setEngineVolume(carDifferent.getEngineVolume());
        carInitial.setBrand(carDifferent.getBrand());
        int newHorsepower = carInitial.getHorsepower() + 500;
        int newProductionYear = carInitial.getProductionYear() + 1;
        carInitial.setHorsepower(newHorsepower);
        carInitial.setProductionYear(newProductionYear);
        boolean success = carRepository.update(carInitial);
        Car carInDb = carRepository.findById(carInitial.getId()).orElse(new Car());
        assertThat(success).isTrue();
        assertThat(carInDb).isEqualTo(carInitial);
        assertThat(carInDb.getBodyStyle()).isEqualTo(carInitial.getBodyStyle());
        assertThat(carInDb.getExteriorColor()).isEqualTo(carInitial.getExteriorColor());
        assertThat(carInDb.getTransmissionType()).isEqualTo(carInitial.getTransmissionType());
        assertThat(carInDb.getDrivetrain()).isEqualTo(carInitial.getDrivetrain());
        assertThat(carInDb.getSteeringWheelSide()).isEqualTo(SteeringWheelSide.RIGHT);
        assertThat(carInDb.getModelName()).isEqualTo(carInitial.getModelName());
        assertThat(carInDb.getEngineType()).isEqualTo(carInitial.getEngineType());
        assertThat(carInDb.getEngineVolume()).isEqualTo(carInitial.getEngineVolume());
        assertThat(carInDb.getBrand()).isEqualTo(carInitial.getBrand());
        assertThat(carInDb.getHorsepower()).isEqualTo(carInitial.getHorsepower());
        assertThat(carInDb.getProductionYear()).isEqualTo(carInitial.getProductionYear());
    }

    @Test
    public void whenDelete() {
        Car car = makeCar();
        carRepository.add(car);
        int carId = car.getId();
        carRepository.delete(car);
        Optional<Car> carInDb = carRepository.findById(carId);
        assertThat(carInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        Car car = makeCar();
        carRepository.add(car);
        int carId = car.getId();
        carRepository.delete(carId);
        Optional<Car> carInDb = carRepository.findById(carId);
        assertThat(carInDb).isEmpty();
    }

    private Car makeCar() {
        String value = String.valueOf(System.currentTimeMillis());
        BodyStyle bodyStyle = new BodyStyle(0, value);
        bodyStyleRepository.add(bodyStyle);
        ExteriorColor exteriorColor = new ExteriorColor(0, value);
        exteriorColorRepository.add(exteriorColor);
        TransmissionType transmissionType = new TransmissionType(0, value);
        transmissionTypeRepository.add(transmissionType);
        Drivetrain drivetrain = new Drivetrain(0, value);
        drivetrainRepository.add(drivetrain);
        EngineType engineType = new EngineType(0, value);
        engineTypeRepository.add(engineType);
        EngineVolume engineVolume = new EngineVolume(0, value);
        engineVolumeRepository.add(engineVolume);
        Brand brand = new Brand(0, value);
        brandRepository.add(brand);
        return new Car(
                0,
                bodyStyle,
                exteriorColor,
                transmissionType,
                drivetrain,
                SteeringWheelSide.LEFT,
                value,
                engineType,
                engineVolume,
                brand,
                1000,
                2020,
                new HashSet<>()
        );
    }
}