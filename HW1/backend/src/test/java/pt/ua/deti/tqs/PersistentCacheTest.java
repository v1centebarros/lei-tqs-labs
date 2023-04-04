package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.cache.LocalCache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersistentCacheTest {


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
    void testcontainsKey() {
        cache.put("key", 1234);
        assertTrue(cache.containsKey("key"));
    }

    @Test
    void testGetKeys() {
        cache.put("key1", 1234);
        cache.put("key2", 5678);
        assertEquals(2, cache.size());
    }

    @Test
    void testRemove() {
        cache.put("key1", 1234);
        cache.put("key2", 5678);
        cache.remove("key1");
        assertEquals(1, cache.size());
    }












}
