package pt.ua.deti.tqs;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class CacheStatsControllerIT {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void whenGetCacheStatsThenReturnsStats() {
        RestAssured.when()
                .get("/api/cache/stats")
                .then()
                .statusCode(200)
                .body("airQualityStats.hits", is(instanceOf(Number.class)))
                .body("airQualityStats.misses", is(instanceOf(Number.class)))
                .body("airQualityStats.puts", is(instanceOf(Number.class)))
                .body("airQualityStats.requests", is(instanceOf(Number.class)))
                .body("forecastCacheStats.hits", is(instanceOf(Number.class)))
                .body("forecastCacheStats.misses", is(instanceOf(Number.class)))
                .body("forecastCacheStats.puts", is(instanceOf(Number.class)))
                .body("forecastCacheStats.requests", is(instanceOf(Number.class)));

    }
}
