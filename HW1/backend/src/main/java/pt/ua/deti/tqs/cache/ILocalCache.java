package pt.ua.deti.tqs.cache;


public interface ILocalCache<K,V> {

    V get(K key);
    void put(K key, V value);
    boolean containsKey(K key);
    int size();
    void remove(K key);
    Iterable<K> getKeys();
}