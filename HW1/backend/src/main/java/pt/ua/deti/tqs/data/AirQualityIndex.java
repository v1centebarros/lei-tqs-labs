package pt.ua.deti.tqs.data;

import java.lang.reflect.Array;
import java.util.Arrays;

public enum AirQualityIndex {
    GOOD(5),
    FAIR(4),
    MODERATE(3),
    POOR(2),
    VERY_POOR(1),
    ;

    private final int value;

    AirQualityIndex(int v ) {
        value = v;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static AirQualityIndex fromValue(int value) {
        return Arrays.stream(AirQualityIndex.values())
                .filter(airQualityIndex -> airQualityIndex.value == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
