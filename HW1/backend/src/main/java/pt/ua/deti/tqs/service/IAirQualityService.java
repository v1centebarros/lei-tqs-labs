package pt.ua.deti.tqs.service;

import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;

import java.util.List;

public interface IAirQualityService {
    AirQuality getAirQualityNinja(String city);

    AirQuality getAirQualityOpenWeather(Double lat, Double lon);

    AirQuality getAirQuality(City city);

    List<AirQuality> getAirQualityForecast(City city);
}
