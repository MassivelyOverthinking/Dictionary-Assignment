import java.util.Queue;

public class Bucket<K, V> {
    private int size;
    private int capacity;
    private bucketEntry<K, V>[] slots;

    public Bucket(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.slots =  initializeBuckets(capacity);
    }

    @SuppressWarnings("unchecked")
    private bucketEntry<K, V>[] initializeBuckets(int capacity) {
        return (bucketEntry<K, V>[]) new bucketEntry[capacity];
    }
}
