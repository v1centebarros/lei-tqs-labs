package pt.ua.deti.tqs.cache;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CacheStats {

    private Integer hits;
    private Integer misses;
    private Integer puts;

    public CacheStats() {
        hits = 0;
        misses = 0;
        puts = 0;
    }

    public void hit() {
        hits++;
    }

    public void miss() {
        misses++;
    }

    public void put() {
        puts++;
    }

    public Integer getRequests() {
        return hits + misses;
    }
}
