package pt.ua.deti.tqs.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalCache<K, V> implements ILocalCache<K, V> {

    private final Map<K, V> cache;

    public LocalCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
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

}
