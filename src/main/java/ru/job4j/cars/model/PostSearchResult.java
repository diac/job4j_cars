package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import ru.job4j.cars.enumeration.SteeringWheelSide;

import javax.persistence.*;

@Entity
@Immutable
@Table(name = "post_search")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchResult {

    @Id
    @Column(name = "post_id")
    private int postId;

    @Column(name = "car_id")
    private int carId;

    @Column(name = "body_style_id")
    private int bodyStyleId;

    @Column(name = "exterior_color_id")
    private int exteriorColorId;

    @Column(name = "transmission_type_id")
    private int transmissionTypeId;

    @Column(name = "drivetrain_id")
    private int drivetrainId;

    @Enumerated(EnumType.STRING)
    @Column(name = "steering_wheel_side")
    private SteeringWheelSide steeringWheelSide;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "engine_type_id")
    private int engineTypeId;

    @Column(name = "brand_id")
    private int brandId;

    @Column(name = "horsepower")
    private int horsepower;

    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "engine_volume")
    private int engineVolume;
}