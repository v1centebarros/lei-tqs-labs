package pt.ua.deti.tqs.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AirQuality {
    @JsonProperty("overallAqi")
    @JsonAlias("overall_aqi")
    private Integer overallAqi;

    @JsonProperty("CO")
    private Measurement co;

    @JsonProperty("PM10")
    private Measurement pm10;

    @JsonProperty("SO2")
    private Measurement so2;

    @JsonProperty("PM25")
    @JsonAlias("PM2.5")
    private Measurement pm25;

    @JsonProperty("O3")
    private Measurement o3;

    @JsonProperty("NO2")
    private Measurement no2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirQuality that = (AirQuality) o;
        return Objects.equals(overallAqi, that.overallAqi) && Objects.equals(co, that.co) && Objects.equals(pm10, that.pm10) && Objects.equals(so2, that.so2) && Objects.equals(pm25, that.pm25) && Objects.equals(o3, that.o3) && Objects.equals(no2, that.no2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(overallAqi, co, pm10, so2, pm25, o3, no2);
    }
}
