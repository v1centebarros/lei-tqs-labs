package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.controller.AirQualityController;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;
import pt.ua.deti.tqs.service.AirQualityService;
import pt.ua.deti.tqs.service.CacheService;
import pt.ua.deti.tqs.service.CityService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(AirQualityController.class)
class AirQualityControllerWithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService service;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private CityService cityService;
    private AirQuality airQuality;
    private List<AirQuality> airQualityForecast;
    private City city;
    @BeforeEach
    void setUp() {
        airQuality = new AirQuality(12, Arrays.asList(12, 12, 12, 12, 12, 12));
        city = new City("Porto", 12, 12, "Porto, Portugal");
        airQualityForecast = Arrays.asList(airQuality, airQuality, airQuality, airQuality, airQuality);
    }

    @Test
    void whenGetQualityFromCityThenReturnsAirQuality(){

        when(cityService.getCity(Mockito.any())).thenReturn(city);
        when(service.getAirQuality(Mockito.any())).thenReturn(airQuality);
        try {
            mvc.perform(get("/api/quality?city=Porto")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.city.name", is("porto")))
                    .andExpect(jsonPath("$.city.displayName", is("Porto, Portugal")))
                    .andExpect(jsonPath("$.city.latitude", is(12.0)))
                    .andExpect(jsonPath("$.city.longitude", is(12.0)))
                    .andExpect(jsonPath("$.data.aqi", is(12)))
                    .andExpect(jsonPath("$.data.pm10", is(12)))
                    .andExpect(jsonPath("$.data.pm25", is(12)))
                    .andExpect(jsonPath("$.data.no2", is(12)))
                    .andExpect(jsonPath("$.data.so2", is(12)))
                    .andExpect(jsonPath("$.data.o3", is(12)))
                    .andExpect(jsonPath("$.data.co", is(12)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(1)).getAirQuality(Mockito.any());
    }

    @Test
    void whenGetQualityFromCityThenReturnsError(){
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        when(service.getAirQuality(Mockito.any())).thenReturn(null);
        try {
            mvc.perform(get("/api/quality?city=Porto")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the air quality data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(1)).getAirQuality(Mockito.any());
    }

    @Test
    void whenGetQualityFromInvalidCityThenReturnsError(){
        when(service.getAirQuality(Mockito.any())).thenReturn(null);
        when(cityService.getCity(Mockito.any())).thenReturn(null);
        try {
            mvc.perform(get("/api/quality?city=aqwervr")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the city data")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(0)).getAirQuality(Mockito.any());

    }

    @Test
    void whenGetForecastFromCityThenReturnsAirQuality() {
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        when(service.getAirQualityForecast(Mockito.any())).thenReturn(airQualityForecast);
        try {
            mvc.perform(get("/api/forecast?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(5)))
                    .andExpect(jsonPath("$.data[0].aqi", is(12)))
                    .andExpect(jsonPath("$.data[0].pm10", is(12)))
                    .andExpect(jsonPath("$.data[0].pm25", is(12)))
                    .andExpect(jsonPath("$.data[0].no2", is(12)))
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(1)).getAirQualityForecast(Mockito.any());

    }

    @Test
    void whenGetForecastFromCityThenReturnsError() {
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        when(service.getAirQualityForecast(Mockito.any())).thenReturn(null);
        try {
            mvc.perform(get("/api/forecast?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the forecast data")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(1)).getAirQualityForecast(Mockito.any());
    }

    @Test
    void whenGetForecastFromInvalidCityThenReturnsError() {

        when(service.getAirQualityForecast(Mockito.any())).thenReturn(null);
        when(cityService.getCity(Mockito.any())).thenReturn(null);

        try {
            mvc.perform(get("/api/forecast?city=aqwervr")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the city data")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(0)).getAirQualityForecast(Mockito.any());
    }

    @Test
    void whenGetCityReturnCityData() {
        when(cityService.getCity(Mockito.any())).thenReturn(city);

        try {
            mvc.perform(get("/api/city?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("porto")))
                    .andExpect(jsonPath("$.displayName", is("Porto, Portugal")))
                    .andExpect(jsonPath("$.latitude", is(12.0)))
                    .andExpect(jsonPath("$.longitude", is(12.0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(0)).getAirQuality(Mockito.any());
    }

    @Test
    void whenGetCityFromInvalidCityThenReturnsError() {
        when(cityService.getCity(Mockito.any())).thenReturn(null);

        try {
            mvc.perform(get("/api/city?city=aqwervr")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the city data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(cityService, times(1)).getCity(Mockito.any());
        verify(service, times(0)).getAirQuality(Mockito.any());
    }

    @Test
    void whenGetAirQualityFromOpenWeatherThenReturnsAirQuality() {
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        when(service.getFromOpenWeather(city)).thenReturn(airQuality);
        try {
            mvc.perform(get("/api/openweather?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.aqi", is(12)))
                    .andExpect(jsonPath("$.data.pm10", is(12)))
                    .andExpect(jsonPath("$.data.pm25", is(12)))
                    .andExpect(jsonPath("$.data.no2", is(12)))
                    .andExpect(jsonPath("$.data.so2", is(12)))
                    .andExpect(jsonPath("$.data.o3", is(12)))
                    .andExpect(jsonPath("$.data.co", is(12)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(service, times(1)).getFromOpenWeather(Mockito.any());
        verify(cityService, times(1)).getCity(Mockito.any());
    }

    @Test
    void whenGetAirQualityFromOpenWeatherThenReturnsError() {
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        when(service.getFromOpenWeather(city)).thenReturn(null);
        try {
            mvc.perform(get("/api/openweather?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the air quality data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(service, times(1)).getFromOpenWeather(Mockito.any());
        verify(cityService, times(1)).getCity(Mockito.any());
    }

    @Test
    void whenGetAirQualityFromOpenWeatherWithInvalidCityThenReturnsError () {
        when(cityService.getCity(Mockito.any())).thenReturn(null);
        when(service.getFromOpenWeather(city)).thenReturn(null);

        try {
            mvc.perform(get("/api/openweather?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the city data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(service, times(0)).getFromOpenWeather(Mockito.any());
        verify(cityService, times(1)).getCity(Mockito.any());
    }

    @Test
    void whenGetAirQualityFromNinjaThenReturnsAirQuality () {
        when(service.getFromNinja(city)).thenReturn(airQuality);
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        try {
            mvc.perform(get("/api/ninja?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.aqi", is(12)))
                    .andExpect(jsonPath("$.data.pm10", is(12)))
                    .andExpect(jsonPath("$.data.pm25", is(12)))
                    .andExpect(jsonPath("$.data.no2", is(12)))
                    .andExpect(jsonPath("$.data.so2", is(12)))
                    .andExpect(jsonPath("$.data.o3", is(12)))
                    .andExpect(jsonPath("$.data.co", is(12)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(service, times(1)).getFromNinja(Mockito.any());
        verify(cityService, times(1)).getCity(Mockito.any());
    }

    @Test
    void whenGetAirQualityFromNinjaThenReturnsError() {
        when(service.getFromNinja(city)).thenReturn(null);
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        try {
            mvc.perform(get("/api/ninja?city=Porto")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the air quality data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(service, times(1)).getFromNinja(Mockito.any());
        verify(cityService, times(1)).getCity(Mockito.any());
    }

    @Test
    void whenGetAirQualityFromNinjaFromInvalidCityThenReturnsError() {
        when(cityService.getCity(Mockito.any())).thenReturn(null);
        when(service.getFromNinja(city)).thenReturn(null);

        try {
            mvc.perform(get("/api/ninja?city=asdsadsasd")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the city data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(service, times(0)).getFromNinja(Mockito.any());
        verify(cityService, times(1)).getCity(Mockito.any());
    }

}
