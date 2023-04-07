package pt.ua.deti.tqs.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pt.ua.deti.tqs.cache.CacheStats;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class CacheStatsDTO {
    private CacheStats airQualityStats;
    private CacheStats forecastCacheStats;
}
