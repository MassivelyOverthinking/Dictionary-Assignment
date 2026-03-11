import java.util.ArrayList;

public class CuckooDictionary<K, V> {
    private int size;
    private int capacity;
    private double threshold;
    private Bucket<K, V>[] firstLayer;
    private Bucket<K, V>[] secondLayer;
    private ArrayList<Integer> occupiedList;

    public CuckooDictionary(int capacity, double threshold) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity can not be 0 or below");
        }

        if (threshold <= 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("Threshold must be between 0.0 and 1.0");
        }

        this.size = 0;
        this.capacity = capacity;
        this.threshold = threshold;
        this.firstLayer = initializeLayers(capacity);
        this.secondLayer = initializeLayers(capacity);
        this.occupiedList = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private Bucket<K, V>[] initializeLayers(int capacity) {
        return (Bucket<K, V>[]) new Bucket[capacity];
    }

    private double loadFactor() {
        return (double) this.size / this.capacity;
    }

    private boolean rehashNeeded() {
        return this.loadFactor() >= threshold;
    }


}
