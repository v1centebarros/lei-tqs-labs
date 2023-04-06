package pt.ua.deti.tqs.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ForecastDTO {
    private City city;
    private List<AirQuality> data;
}
