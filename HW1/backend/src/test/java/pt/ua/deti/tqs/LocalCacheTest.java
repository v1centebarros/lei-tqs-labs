package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.cache.LocalCache;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    private LocalCache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LocalCache<>();
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
    void testGetWithExpiredEntry() throws InterruptedException {
        cache.put("key", 1234, 1);
        Thread.sleep(2000);
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
    void testGetCacheWithExpiredEntry() throws InterruptedException {
        cache.put("key1", 1234, 1);
        cache.put("key2", 5678, 10);
        Thread.sleep(3000);
        assertEquals(2, cache.size());
    }
}
