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
import pt.ua.deti.tqs.service.CacheService;
import pt.ua.deti.tqs.service.CityService;
import pt.ua.deti.tqs.service.IAirQualityService;

import java.util.Arrays;

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
    private IAirQualityService service;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private CityService cityService;
    private AirQuality airQuality;
    private City city;
    @BeforeEach
    void setUp() {
        airQuality = new AirQuality(12, Arrays.asList(12,12,12,12,12,12));
        city = new City("Porto", 12, 12,"Porto, Portugal");
    }

    @Test
    void whenGetQualityFromCityThenReturnsAirQuality(){

        when(service.getAirQuality(Mockito.any())).thenReturn(airQuality);
        when(cityService.getCity(Mockito.any())).thenReturn(city);
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
    }

    @Test
    void whenGetQualityFromCityThenReturnsError(){
        when(service.getAirQuality(Mockito.any())).thenReturn(null);
        when(cityService.getCity(Mockito.any())).thenReturn(city);
        try {
            mvc.perform(get("/api/quality?city=Porto")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", is("It was not possible to retrieve the air quality data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }
}
