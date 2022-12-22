package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.cars.enumeration.SteeringWheelSide;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchParams {

    private Integer priceMin;
    private Integer priceMax;
    private Integer carBrandId;
    private String carModel;
    private Integer carBodyStyleId;
    private Integer carExteriorColorId;
    private Integer carTransmissionTypeId;
    private Integer carDrivetrainId;
    private SteeringWheelSide carSteeringWheelSide;
    private Integer carEngineTypeId;
    private Integer carEngineVolumeMin;
    private Integer carEngineVolumeMax;
    private Integer carHorsepowerMin;
    private Integer carHorsepowerMax;
    private Integer carProductionYearMin;
    private Integer carProductionYearMax;
    private Integer carKilometrageMax;
}