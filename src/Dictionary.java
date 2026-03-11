import java.util.ArrayList;
import java.util.Arrays;

public class Dictionary<K, V> {
    // Internal variables
    private int size;
    private int capacity;
    private double threshold;
    private ArrayList<Integer> occupiedList;
    private Entry<K, V>[] storage;

    // Default constructor -> User can specify the internal capacity.
    public Dictionary(int capacity, double threshold) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity can not be less that 0.0");
        }

        if (threshold <= 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("Threshold must be between 0.0 and 1.0");
        }

        this.size = 0;
        this.capacity = capacity;
        this.threshold = threshold;
        this.occupiedList = new ArrayList<Integer>();
        this.storage = initializeStorage(capacity);
    }

    // Secondary constructor -> Capacity is statically allocated.
    public Dictionary() {
        this.size = 0;
        this.capacity = 100;
        this.threshold = 0.5;
        this.occupiedList = new ArrayList<>();
        this.storage = initializeStorage(100);
    }

    // Internal helper-method for constructing internal array of Entry-instances.
    @SuppressWarnings("unchecked")
    public Entry<K, V>[] initializeStorage(int capacity) {
        Entry<K, V>[] array = (Entry<K, V>[]) new Entry[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = new Entry<>();
        }
        return array;
    }

    // Internal helper-method for calculating current Load Factor.
    private double loadFactor() {
        return (double) this.size / this.capacity;
    }

    private boolean rehashNeeded() {
        return this.loadFactor() >= this.threshold;
    }

    private int getIndex(int hash) {
        return Math.abs(hash) % this.capacity;
    }

    public boolean insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not by 'null'");
        }

        if (this.rehashNeeded()) {
            this.rehash();
        }

        int hashCode = key.hashCode();
        int index = this.getIndex(hashCode);

        for (int i = 0; i < storage.length; i++) {
            int probeIndex = (index + i) % this.capacity;
            Entry<K, V> entry =  this.storage[probeIndex];
            if (entry.getStatus() == Placeholder.Occupied) {
                if (entry.getKey().equals(key)) {
                    entry.addKeyAndValue(key, value);
                    return true;
                } else {
                    continue;
                }
            } else {
                // Entry is empty or dead -> We insert at this spot.
                entry.addKeyAndValue(key, value);
                this.occupiedList.add(probeIndex);
                size++;
                return true;
            }
        }

        // Unreachable Code -> Should never execute this;
        return false;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null");
        }

        int hashCode = key.hashCode();
        int index = this.getIndex(hashCode);

        for (int i = 0; i < storage.length; i++) {
            int probeIndex = (index + i) % this.capacity;
            Entry<K, V> entry = this.storage[probeIndex];
            Placeholder status = entry.getStatus();

            if (status == Placeholder.Occupied) {
                if (entry.getKey().equals(key)) {
                    V result = entry.getValue();
                    entry.removeKeyAndValue();
                    return result;
                }
            } else if (status == Placeholder.Tombstone) {
                continue;
            } else {
                return null;
            }
        }

        // Unreachable Code -> Should never execute this;
        return null;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null");
        }

        int hashCode = key.hashCode();
        int index = this.getIndex(hashCode);

        for (int i = 0; i < storage.length; i++) {
            int probeIndex = (index + i) % this.capacity;
            Entry<K, V> entry = this.storage[probeIndex];
            Placeholder status = entry.getStatus();

            if (status == Placeholder.Occupied) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            } else if (status == Placeholder.Tombstone) {
                continue;
            } else {
                return null;
            }
        }

        // Unreachable code -> Should never execute this.
        return null;
    }

    // Rehash internal storage-array to increase storage space.
    public void rehash() {
        // Copy the current Storage-array & Occupied-list
        Entry<K, V>[] oldStorage = this.storage;
        ArrayList<Integer> oldOccupied = this.occupiedList;

        // Initialize everything to be new (reset)
        // Double storage space (capacity)
        this.capacity = this.capacity * 2;
        this.storage = initializeStorage(this.capacity);
        this.occupiedList = new ArrayList<>();
        this.size = 0;

        // Iterate through old occupied-list & insert them into new list
        for (Integer index: oldOccupied) {
            Entry<K, V> entry = oldStorage[index];

            // Safety check -> No null values & Entry is Occupied
            if (entry.getStatus() == Placeholder.Occupied && entry.getKey() != null) {
                this.insert(entry.getKey(), entry.getValue());
            }
        }
    }

    // Helper-method to return all internal keys in Dictionary.
    public ArrayList<K> keys() {
        ArrayList<K> array = new ArrayList<>();
        for (Integer index : occupiedList) {
            array.add(storage[index].getKey());
        }
        return array;
    }

    // Helper-method to return all internal values in Dictionary.
    public ArrayList<V> values() {
        ArrayList<V> array = new ArrayList<>();
        for (Integer index : occupiedList) {
            array.add(storage[index].getValue());
        }
        return array;
    }

    // Internal helper-method for returning internal size.
    public int getSize() {
        return size;
    }

    // Internal helper-method for returning internal capacity.
    public int getCapacity() {
        return capacity;
    }

    // Internal helper-method for determining if the storage array is currently full.
    public boolean isFull() {
        return this.size >= this.capacity;
    }
}
