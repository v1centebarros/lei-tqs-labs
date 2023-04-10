package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.cache.LocalVolatileCache;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class VolatileCacheTest {

    private LocalVolatileCache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LocalVolatileCache<>();
    }

    @Test
    void testPutAndGet() {
        cache.put("key", 1234);
        assertEquals(1234, cache.get("key"));
    }

    @Test
    void testPutWithDefaultTtl() {
        cache.put("key", 5678);
        assertEquals(5678, cache.get("key"));
    }

    @Test
    void testGetWithExpiredEntry() {
        cache.put("key", 1234, 1);
        await().atMost(10, TimeUnit.SECONDS).until(() -> cache.get("key") == null);
        assertNull(cache.get("key"));
    }

    @Test
    void testContainsKey() {
        cache.put("key", 1234, 10);
        assertTrue(cache.containsKey("key"));
    }

    @Test
    void testGetCache() {
        cache.put("key1", 1234, 10);
        cache.put("key2", 5678, 10);
        assertEquals(2, cache.size());
    }

    @Test
    void testGetCacheWithExpiredEntry() {
        cache.put("key1", 1234, 1);
        cache.put("key2", 5678, 10);
        await().atMost(3, TimeUnit.SECONDS);
        assertEquals(2, cache.size());
    }
}
