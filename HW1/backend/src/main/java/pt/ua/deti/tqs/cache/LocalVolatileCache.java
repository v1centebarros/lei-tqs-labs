package pt.ua.deti.tqs.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class LocalVolatileCache<K, V> implements ILocalCache<K, V>{

    private final Map<K, CacheEntry<V>> cache;
    private static final int DEFAULT_TTL = 8;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public LocalVolatileCache() {
        this.cache = new ConcurrentHashMap<>();
        new Thread(new CacheCleanupTask()).start();
    }

    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }
        return null;
    }

    public void put(K key, V value, int ttlSeconds) {
        cache.put(key, new CacheEntry<>(value, ttlSeconds));
    }

    public void put(K key, V value) {
        cache.put(key, new CacheEntry<>(value, DEFAULT_TTL));
    }

    public boolean containsKey(K key) {
        CacheEntry<V> entry = cache.get(key);
        return entry != null && !entry.isExpired();
    }

    public int size() {
        return cache.size();
    }

    public Iterable<K> getKeys() {
        return cache.keySet();
    }

    public void remove(K key) {
        cache.remove(key);
    }

    private class CacheCleanupTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error("Cache cleanup thread interrupted");
                    Thread.currentThread().interrupt();
                }

                for (Map.Entry<K, CacheEntry<V>> entry : cache.entrySet()) {
                    if (entry.getValue().isExpired()) {
                        cache.remove(entry.getKey());
                    }
                }
            }
        }

    }
}
