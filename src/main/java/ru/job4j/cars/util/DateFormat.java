package ru.job4j.cars.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public final class DateFormat {

    private static final String DEFAULT_FORMAT = "dd-MM-yyyy HH:mm";

    private DateFormat() {
    }

    public static Function<LocalDateTime, String> defaultFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
        return localDateTime -> localDateTime.format(formatter);
    }
}