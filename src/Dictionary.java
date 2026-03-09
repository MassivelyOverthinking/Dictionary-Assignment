public class Dictionary<K, V> {
    // Internal variables
    private int size;
    private int capacity;
    private Object[] storage;

    // Default constructor -> User can specify the internal capacity.
    public Dictionary(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be represented by a positive integer")
        }

        this.size = 0;
        this.capacity = capacity;
        this.storage = initilizeStorage(capacity);
    }

    // Secondary constructor -> Capacity is dynamically allocated.
    public Dictionary() {
        this.size = 0;
        this.capacity = 100;
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
    public double loadFactor() {
        return (double) this.size / this.capacity;
    }

    // Internal helper-method for determining if the storage array is currently full.
    public boolean isFull() {
        return this.size >= this.capacity;
    }

    public boolean insert(K key, V value) {
        // TODO!
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
