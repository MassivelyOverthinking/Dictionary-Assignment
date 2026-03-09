public class Dictionary<K, V> {
    // Internal variables
    private int size;
    private int capacity;
    private double threshold;
    private Object[] storage;

    // Default constructor -> User can specify the internal capacity.
    public Dictionary(int capacity, double threshold) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be represented by a positive integer");
        }

        if (threshold <= 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("Threshold must be between 0.0 and 1.0");
        }

        this.size = 0;
        this.capacity = capacity;
        this.storage = initilizeStorage(capacity);
    }

    // Secondary constructor -> Capacity is dynamically allocated.
    public Dictionary() {
        this.size = 0;
        this.capacity = 100;
        this.threshold = 0.5;
        this.storage = initilizeStorage(100);
    }

    // Internal helper-method for constructing internal array of Entry-instances.
    private Object[] initilizeStorage(int capacity) {
        return new Object[capacity];
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    // Internal helper-method for calculating current Load Factor.
    private double loadFactor() {
        return (double) this.size / this.capacity;
    }

    private boolean rehashNeeded() {
        return this.loadFactor() >= this.threshold;
    }

    // Internal helper-method for determining if the storage array is currently full.
    public boolean isFull() {
        return this.size >= this.capacity;
    }

    private int getIndex(int hash) {
        return hash / this.capacity;
    }

    public boolean insert(K key, V value) {
        if (this.rehashNeeded()) {
            this.rehash();
        }

        int hashCode = key.hashCode();
        int index = this.getIndex(hashCode);

        for (int i = index; i < storage.length; i++) {
            Entry<K, V> entry = (Entry<K, V>) this.storage[i];
            if (entry.getStatus() == Placeholder.Occupied) {

            }
        }

    }

    public Entry<K, V> remove(K key) {
        // TODO!
    }

    public Entry<K, V> get(K key) {
        // TODO!
    }

    public void rehash() {
        // TODO!
    }
}
