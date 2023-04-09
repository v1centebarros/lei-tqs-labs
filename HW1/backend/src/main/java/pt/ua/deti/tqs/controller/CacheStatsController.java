package pt.ua.deti.tqs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.tqs.data.CacheStatsDTO;
import pt.ua.deti.tqs.service.AirQualityService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CacheStatsController {

    @Autowired
    private AirQualityService cacheService;

    @GetMapping("/cache/stats")
    public ResponseEntity<Object> getCacheStats() {
        return ResponseEntity.ok(new CacheStatsDTO(cacheService.getAirQualityCacheStats(), cacheService.getAirQualityForecastCacheStats()));
    }
}
