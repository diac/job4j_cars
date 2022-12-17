package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.cars.enumeration.SteeringWheelSide;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchParams {

    private int carBrandId;
    private String carModel;
    private int carBodyStyleId;
    private int carExteriorColorId;
    private int carTransmissionTypeId;
    private int carDrivetrainId;
    private SteeringWheelSide carSteeringWheelSide;
    private int carEngineTypeId;
    private int carEngineVolumeMin;
    private int carEngineVolumeMax;
    private int carHorsepowerMin;
    private int carHorsepowerMax;
    private int carProductionYearMin;
    private int carProductionYearMax;
}