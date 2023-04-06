package pt.ua.deti.tqs.data;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AirQuality {

    private Integer aqi;

    private Number co;

    private Number pm10;

    private Number so2;


    private Number pm25;

    private Number o3;

    private Number no2;

    public AirQuality(Integer aqi, List<Number> measurements) {
        this.aqi = aqi;
        this.co = measurements.get(0);
        this.pm10 = measurements.get(1);
        this.so2 = measurements.get(2);
        this.pm25 = measurements.get(3);
        this.o3 = measurements.get(4);
        this.no2 = measurements.get(5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirQuality that = (AirQuality) o;
        return Objects.equals(aqi, that.aqi) && Objects.equals(co, that.co) && Objects.equals(pm10, that.pm10) && Objects.equals(so2, that.so2) && Objects.equals(pm25, that.pm25) && Objects.equals(o3, that.o3) && Objects.equals(no2, that.no2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aqi, co, pm10, so2, pm25, o3, no2);
    }

    public AirQuality(Integer aqi, Map<String, Number> measurements) {
        this.aqi = aqi;
        this.co = measurements.get("co");
        this.pm10 = measurements.get("pm10");
        this.so2 = measurements.get("so2");
        this.pm25 = measurements.get("pm2_5");
        this.o3 = measurements.get("o3");
        this.no2 = measurements.get("no2");
    }
}

