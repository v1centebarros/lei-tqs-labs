package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.cache.CacheStats;
import pt.ua.deti.tqs.controller.CacheStatsController;
import pt.ua.deti.tqs.service.AirQualityService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CacheStatsController.class)
class CacheStatsControlllerWithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService cacheService;


    @BeforeEach
    public void setup() {

    }

    @Test
    void whenGetCacheStats_thenReturnCacheStats() {
        when(cacheService.getAirQualityCacheStats()).thenReturn(new CacheStats());
        when(cacheService.getAirQualityForecastCacheStats()).thenReturn(new CacheStats());

        try {
            mvc.perform(
                    get("/api/cache/stats")
            ).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cacheService,times(1)).getAirQualityCacheStats();
        verify(cacheService,times(1)).getAirQualityForecastCacheStats();
    }

}
