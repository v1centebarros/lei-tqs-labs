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
import pt.ua.deti.tqs.data.Measurement;
import pt.ua.deti.tqs.service.AirQualityService;
import pt.ua.deti.tqs.service.CacheService;
import pt.ua.deti.tqs.service.CityService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(AirQualityController.class)
class AirQualityController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService service;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private CityService cityService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetQualityFromCity_thenReturnsAirQuality() throws Exception{
        AirQuality airQuality = new AirQuality(
                12,
                new Measurement(12.0,12),
                new Measurement(12.0,12),
                new Measurement(12.0,12),
                new Measurement(12.0,12),
                new Measurement(12.0,12),
                new Measurement(12.0,12)
                );
        when(service.getAirQuality(Mockito.any())).thenReturn(airQuality);

        mvc.perform(get("/api/quality?city=Porto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.overallAqi", is(airQuality.getOverallAqi())))
//                .andExpect(jsonPath("$.PM25.aqi", is(airQuality.getPm25().getAqi())))
                .andExpect(jsonPath("$.PM25.concentration", is(airQuality.getPm25().getConcentration())));

        verify(service, times(1)).getAirQuality(Mockito.any());
    }


    @Test
    void whenGetQualityFromCity_thenReturnsAirQualityFromCache() throws Exception {
        AirQuality airQuality = new AirQuality(
                12,
                new Measurement(12.0, 12),
                new Measurement(12.0, 12),
                new Measurement(12.0, 12),
                new Measurement(12.0, 12),
                new Measurement(12.0, 12),
                new Measurement(12.0, 12)
        );
        when(service.getAirQuality(Mockito.any())).thenReturn(airQuality);

        when(cacheService.hasCityQuality(Mockito.any())).thenReturn(true);
        when(cacheService.getAirQuality(Mockito.any())).thenReturn(airQuality);

        mvc.perform(get("/api/quality?city=Porto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.overallAqi", is(airQuality.getOverallAqi())))

                .andExpect(jsonPath("$.PM25.concentration", is(airQuality.getPm25().getConcentration())));

        verify(service, times(0)).getAirQuality(Mockito.any());
        verify(cacheService, times(1)).getAirQuality(Mockito.any());
    }
}
