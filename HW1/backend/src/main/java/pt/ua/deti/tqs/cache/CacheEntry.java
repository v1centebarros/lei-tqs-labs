package pt.ua.deti.tqs.cache;

public class CacheEntry<V> {
    private final V value;
    private final long expirationTime;

    public CacheEntry(V value, int ttlSeconds) {
        this.value = value;
        this.expirationTime = System.currentTimeMillis() + ttlSeconds * 1000L;
    }

    public V getValue() {
        return value;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}