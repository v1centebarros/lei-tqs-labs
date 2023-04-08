package pt.ua.deti.tqs.apis;

import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;

import java.util.List;

@Service
public interface ApiQuality {
    AirQuality getQuality(City c);

    List<AirQuality> getForecast(City c);

    String getApiName();
}
