package pt.ua.deti.tqs;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;
import pt.ua.deti.tqs.service.CacheService;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CacheServiceUnitTests {

    private CacheService cacheService;
    private City city1;
    private City city2;
    private AirQuality airQuality1;
    private AirQuality airQuality2;

    @BeforeEach
    void setUp() {
        cacheService = new CacheService();
        city1 = new City("Porto", 41.1496, -8.6109, "Porto, PT");
        city2 = new City("Lisbon", 38.7223, -9.1393, "Lisbon, PT");
        airQuality1 = new AirQuality(50, Arrays.asList(1, 2, 3, 4, 5, 6));
        airQuality2 = new AirQuality(100, Arrays.asList(7, 8, 9, 10, 11, 12));
    }

    @Test
    @DisplayName("Test if hasCityQuality returns true if air quality cached for city")
    void hasCityQuality_returnsTrueIfAirQualityCachedForCity() {
        cacheService.addAirQuality(city1, airQuality1);

        assertThat(cacheService.hasCityQuality(city1), is(true));
        assertThat(cacheService.hasCityQuality(city2), is(false));
    }

    @Test
    @DisplayName("Test if getAirQuality returns cached air quality for city")
    void getAirQuality_returnsCachedAirQualityForCity() {
        cacheService.addAirQuality(city1, airQuality1);

        assertThat(cacheService.getAirQuality(city1), is(equalTo(airQuality1)));
        assertThat(cacheService.getAirQuality(city2), is(nullValue()));
    }

    @Test
    @DisplayName("Test if addAirQuality adds air quality to cache for city")
    void addAirQuality_addsAirQualityToCacheForCity() {
        cacheService.addAirQuality(city1, airQuality1);

        assertThat(cacheService.hasCityQuality(city1), is(true));
        assertThat(cacheService.getAirQuality(city1), is(equalTo(airQuality1)));
    }

    @Test
    @DisplayName("Test if hasCityForecast returns true if air quality forecast cached for city")
    void hasCityForecast_returnsTrueIfAirQualityForecastCachedForCity() {
        cacheService.addAirQualityForecast(city1, Arrays.asList(airQuality1, airQuality2));

        assertThat(cacheService.hasCityForecast(city1), is(true));
        assertThat(cacheService.hasCityForecast(city2), is(false));
    }

    @Test
    @DisplayName("Test if getAirQualityForecast returns cached air quality forecast for city")
    void getAirQualityForecast_returnsCachedAirQualityForecastForCity() {
        cacheService.addAirQualityForecast(city1, Arrays.asList(airQuality1, airQuality2));

        assertThat(cacheService.getAirQualityForecast(city1), containsInAnyOrder(airQuality1, airQuality2));
        assertThat(cacheService.getAirQualityForecast(city2), is(nullValue()));
    }

    @Test
    @DisplayName("Test if addAirQualityForecast adds air quality forecast to cache for city")
    void addAirQualityForecast_addsAirQualityForecastToCacheForCity() {
        cacheService.addAirQualityForecast(city1, Arrays.asList(airQuality1, airQuality2));
        assertThat(cacheService.hasCityForecast(city1), is(true));
        assertThat(cacheService.getAirQualityForecast(city1), containsInAnyOrder(airQuality1, airQuality2));
    }
}
