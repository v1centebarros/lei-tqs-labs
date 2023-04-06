package pt.ua.deti.tqs.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AirQualityDTO {
    private City city;
    private AirQuality data;
}


