package ru.job4j.cars.util;

import ru.job4j.cars.model.Car;

import java.util.function.Function;

public final class Cars {

    private Cars() {

    }

    public static Function<Car, String> displayNameHelper() {
        return car -> String.format(
                "%s %s, %s, %s (%s, %d, %d л/с)",
                car.getBrand().getName(),
                car.getModelName(),
                car.getExteriorColor().getName(),
                car.getBodyStyle().getName(),
                car.getEngineType().getName(),
                car.getEngineVolume(),
                car.getHorsepower()
        );
    }
}