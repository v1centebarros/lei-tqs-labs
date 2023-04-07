package pt.ua.deti.tqs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.tqs.data.CacheStatsDTO;
import pt.ua.deti.tqs.service.AirQualityService;

@RestController
@RequestMapping("/api")
public class CacheStatsController {

    @Autowired
    private AirQualityService cacheService;

    @RequestMapping("/cache/stats")
    public ResponseEntity<Object> getCacheStats() {
        return ResponseEntity.ok(new CacheStatsDTO(cacheService.getAirQualityCacheStats(), cacheService.getAirQualityForecastCacheStats()));
    }
}