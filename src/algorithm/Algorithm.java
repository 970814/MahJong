package algorithm;

@FunctionalInterface
public interface Algorithm<K, V> {
    V search(K t);
}