package pt.ua.deti.tqs.service;

import pt.ua.deti.tqs.data.AirQuality;

import java.util.List;

public interface IAirQualityService {
    AirQuality getAirQualityNinja(String city);

    AirQuality getAirQualityOpenWeather(Double lat, Double lon);

    AirQuality getAirQuality(String city);

    List<AirQuality> getAirQualityForecast(String city);
}
