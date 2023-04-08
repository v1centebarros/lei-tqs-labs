package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ua.deti.tqs.apis.ApiQuality;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;
import pt.ua.deti.tqs.service.AirQualityService;
import pt.ua.deti.tqs.service.CacheService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


@SpringBootTest
class AirQualityServiceUnitTests {

    @MockBean(name = "api1")
    private ApiQuality api1;

    @MockBean(name = "api2")
    private ApiQuality api2;

    @MockBean
    private CacheService cacheService;

    private AirQualityService airQualityService;

    private AirQuality airQuality;

    private List<AirQuality> forecast;

    private City city;

    @BeforeEach
    public void setUp() {
        city = new City("Porto", 41.1496, -8.6109, "Porto, Portugal");
        airQuality = new AirQuality(50, Arrays.asList(1, 2, 3, 4, 5, 6));
        forecast = Arrays.asList(airQuality, airQuality, airQuality, airQuality, airQuality);
        when(api1.getApiName()).thenReturn("NinjaAPI");
        when(api2.getApiName()).thenReturn("OpenWeatherAPI");
        airQualityService = new AirQualityService(new ApiQuality[]{api1, api2}, cacheService);
    }

    @Test
    void whenGetAirQualityFromTheFirstApi_thenReturnAirQuality() {
        when(api1.getQuality(city)).thenReturn(airQuality);
        when(api2.getQuality(city)).thenReturn(null);

        assertThat(airQualityService.getAirQuality(city), is(equalTo(airQuality)));

        verify(api1, times(1)).getQuality(city);
        verify(api2, times(0)).getQuality(city);
        verify(cacheService, times(1)).addAirQuality(city, airQuality);
        verify(cacheService, times(1)).hasCityQuality(city);
        verify(cacheService, times(0)).getAirQuality(city);
    }

    @Test
    void whenGetAirQualityFromTheSecondApi_thenReturnAirQuality() {
        when(api1.getQuality(city)).thenReturn(null);
        when(api2.getQuality(city)).thenReturn(airQuality);

        assertThat(airQualityService.getAirQuality(city), is(equalTo(airQuality)));

        verify(api1, times(1)).getQuality(city);
        verify(api2, times(1)).getQuality(city);
        verify(cacheService, times(1)).addAirQuality(city, airQuality);
        verify(cacheService, times(1)).hasCityQuality(city);
        verify(cacheService, times(0)).getAirQuality(city);
    }

    @Test
    void whenGetAirQualityFromBothApis_thenReturnAirQualityFromTheFirstApi() {
        when(api1.getQuality(city)).thenReturn(airQuality);
        when(api2.getQuality(city)).thenReturn(new AirQuality(100, Arrays.asList(7, 8, 9, 10, 11, 12)));

        assertThat(airQualityService.getAirQuality(city), is(equalTo(airQuality)));

        verify(api1, times(1)).getQuality(city);
        verify(api2, times(0)).getQuality(city);
        verify(cacheService, times(1)).addAirQuality(city, airQuality);
        verify(cacheService, times(1)).hasCityQuality(city);
        verify(cacheService, times(0)).getAirQuality(city);
    }

    @Test
    void whenGetFromCache_thenReturnAirQuality() {
        when(cacheService.hasCityQuality(city)).thenReturn(true);
        when(cacheService.getAirQuality(city)).thenReturn(airQuality);

        assertThat(airQualityService.getAirQuality(city), is(equalTo(airQuality)));

        verify(api1, times(0)).getQuality(city);
        verify(api2, times(0)).getQuality(city);
        verify(cacheService, times(0)).addAirQuality(city, airQuality);
        verify(cacheService, times(1)).hasCityQuality(city);
        verify(cacheService, times(1)).getAirQuality(city);
    }

    @Test
    void whenFailingToGetAirQuality_thenReturnNull() {
        when(api1.getQuality(city)).thenReturn(null);
        when(api2.getQuality(city)).thenReturn(null);
        when(cacheService.hasCityQuality(city)).thenReturn(false);

        assertThat(airQualityService.getAirQuality(city), is(equalTo(null)));

        verify(api1, times(1)).getQuality(city);
        verify(api2, times(1)).getQuality(city);
        verify(cacheService, times(0)).addAirQuality(city, airQuality);
        verify(cacheService, times(1)).hasCityQuality(city);
        verify(cacheService, times(0)).getAirQuality(city);
    }

    @Test
    void whenGetAirQualityForecastFromTheFirstApi_thenReturnAirQuality() {
        when(api1.getForecast(city)).thenReturn(forecast);
        when(api2.getForecast(city)).thenReturn(null);

        assertThat(airQualityService.getAirQualityForecast(city), is(equalTo(forecast)));

        verify(api1, times(1)).getForecast(city);
        verify(api2, times(0)).getForecast(city);
        verify(cacheService, times(1)).addAirQualityForecast(city, forecast);
        verify(cacheService, times(1)).hasCityForecast(city);
    }


    @Test
    void whenGetAirQualityForecastFromTheSecondApi_thenReturnAirQuality() {
        when(api1.getForecast(city)).thenReturn(Collections.emptyList());
        when(api2.getForecast(city)).thenReturn(forecast);

        assertThat(airQualityService.getAirQualityForecast(city), is(equalTo(forecast)));

        verify(api1, times(1)).getForecast(city);
        verify(api2, times(1)).getForecast(city);
        verify(cacheService, times(1)).addAirQualityForecast(city, forecast);
        verify(cacheService, times(1)).hasCityForecast(city);
    }


    @Test
    void whenGetAirQualityForecastFromBothApis_thenReturnAirQualityFromTheFirstApi() {
        when(api1.getForecast(city)).thenReturn(forecast);
        when(api2.getForecast(city)).thenReturn(Arrays.asList(airQuality, airQuality, airQuality, airQuality, airQuality));

        assertThat(airQualityService.getAirQualityForecast(city), is(equalTo(forecast)));

        verify(api1, times(1)).getForecast(city);
        verify(api2, times(0)).getForecast(city);
        verify(cacheService, times(1)).addAirQualityForecast(city, forecast);
        verify(cacheService, times(1)).hasCityForecast(city);
    }


    @Test
    void whenGetAirQualityForecastFromCache_thenReturnAirQuality() {
        when(cacheService.hasCityForecast(city)).thenReturn(true);
        when(cacheService.getAirQualityForecast(city)).thenReturn(forecast);

        assertThat(airQualityService.getAirQualityForecast(city), is(equalTo(forecast)));

        verify(api1, times(0)).getForecast(city);
        verify(api2, times(0)).getForecast(city);
        verify(cacheService, times(0)).addAirQualityForecast(city, forecast);
        verify(cacheService, times(1)).hasCityForecast(city);
        verify(cacheService, times(1)).getAirQualityForecast(city);
    }


    @Test
    void whenFailingToGetAirQualityForecast_thenReturnNull() {
        when(api1.getForecast(city)).thenReturn(Collections.emptyList());
        when(api2.getForecast(city)).thenReturn(Collections.emptyList());
        when(cacheService.hasCityForecast(city)).thenReturn(false);

        assertThat(airQualityService.getAirQualityForecast(city), is(equalTo(Collections.emptyList())));

        verify(api1, times(1)).getForecast(city);
        verify(api2, times(1)).getForecast(city);
        verify(cacheService, times(0)).addAirQualityForecast(city, forecast);
        verify(cacheService, times(1)).hasCityForecast(city);
        verify(cacheService, times(0)).getAirQualityForecast(city);
    }

    @Test
    void whenGetFromNinjaApi_thenReturnAirQuality() {
        when(api1.getQuality(city)).thenReturn(airQuality);

        assertThat(airQualityService.getFromNinja(city), is(equalTo(airQuality)));

        verify(api1, times(1)).getQuality(city);
    }

    @Test
    void whenFailToGetFromNinjaApi_thenReturnNull() {
        when(api1.getQuality(city)).thenReturn(null);

        assertThat(airQualityService.getFromNinja(city), is(equalTo(null)));

        verify(api1, times(1)).getQuality(city);
    }


    @Test
    void whenGetFromOpenWeatherApi_thenReturnAirQuality() {
        when(api2.getQuality(city)).thenReturn(airQuality);

        assertThat(airQualityService.getFromOpenWeather(city), is(equalTo(airQuality)));

        verify(api2, times(1)).getQuality(city);
    }

    @Test
    void whenFailToGetFromOpenWeatherApi_thenReturnNull() {
        when(api2.getQuality(city)).thenReturn(null);

        assertThat(airQualityService.getFromOpenWeather(city), is(equalTo(null)));

        verify(api2, times(1)).getQuality(city);
    }


}
