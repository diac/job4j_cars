package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.job4j.cars.enumeration.SteeringWheelSide;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "body_style_id")
    private BodyStyle bodyStyle;

    @ManyToOne
    @JoinColumn(name = "exterior_color_id")
    private ExteriorColor exteriorColor;

    @ManyToOne
    @JoinColumn(name = "transmission_type_id")
    private TransmissionType transmissionType;

    @ManyToOne
    @JoinColumn(name = "drivetrain_id")
    private Drivetrain drivetrain;

    @Enumerated(EnumType.STRING)
    @Column(name = "steering_wheel_side")
    private SteeringWheelSide steeringWheelSide;

    @Column(name = "model_name")
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "engine_type_id")
    private EngineType engineType;

    @Column(name = "engine_volume")
    private int engineVolume;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "horsepower")
    private int horsepower;

    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "kilometrage")
    private int kilometrage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "history_owner",
            joinColumns = {@JoinColumn(name = "car_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "driver_id", nullable = false, updatable = false)}
    )
    private Set<Driver> owners = new HashSet<>();
}