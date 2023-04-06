package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.controller.AirQualityController;
import pt.ua.deti.tqs.data.AirQuality;
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
class AirQualityController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IAirQualityService service;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private CityService cityService;

    private AirQuality airQuality;
    @BeforeEach
    void setUp() {
        airQuality = new AirQuality(12, Arrays.asList(12,12,12,12,12,12));
    }

    @Test
    void whenGetQualityFromCity_thenReturnsAirQuality() throws Exception{
        when(service.getAirQuality(Mockito.any())).thenReturn(airQuality);

        mvc.perform(get("/api/quality?city=Porto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aqi", is(airQuality.getAqi())))
                .andExpect(jsonPath("$.co", is(airQuality.getCo())))
                .andExpect(jsonPath("$.pm10", is(airQuality.getPm10())))
                .andExpect(jsonPath("$.so2", is(airQuality.getSo2())))
                .andExpect(jsonPath("$.pm25", is(airQuality.getPm25())))
                .andExpect(jsonPath("$.o3", is(airQuality.getO3())))
                .andExpect(jsonPath("$.no2", is(airQuality.getNo2())))
                .andExpect(jsonPath("$.dateTime", is(airQuality.getDateTime().toString())));

        verify(service, times(1)).getAirQuality(Mockito.any());
    }

}
