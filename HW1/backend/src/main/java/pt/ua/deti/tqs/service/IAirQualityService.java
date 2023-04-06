package pt.ua.deti.tqs.service;

import pt.ua.deti.tqs.data.AirQuality;

public interface IAirQualityService {
    AirQuality getAirQualityNinja(String city);

    AirQuality getAirQualityOpenWeather(Double lat, Double lon);


    AirQuality getAirQuality(String city);
}
