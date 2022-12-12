package ru.job4j.cars.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SteeringWheelSide {

    LEFT("Левый"),
    RIGHT("Правый");

    private final String displayName;
}