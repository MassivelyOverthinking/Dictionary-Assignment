import java.util.ArrayList;

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
    private Entry<K, V>[] initializeStorage(int capacity) {
        // Create teh array.
        Entry<K, V>[] array = (Entry<K, V>[]) new Entry[capacity];
        // Iterate and crete the Entry-instances.
        for (int i = 0; i < capacity; i++) {
            array[i] = new Entry<>();
        }
        return array;
    }

    // Internal helper-method for calculating current Load Factor.
    private double loadFactor() {
        return (double) this.size / this.capacity;
    }

    // Internal helper-method to determine if Rehash is needed.
    private boolean rehashNeeded() {
        return this.loadFactor() >= this.threshold;
    }

    // Get the internal Index by HashCode.
    private int getIndex(int hash) {
        return Math.abs(hash) % this.capacity;
    }

    // Main Insert-method -> Key & Value.
    public V insert(K key, V value) {
        // Check if key is null -> Cannot Hash
        if (key == null) {
            throw new IllegalArgumentException("Key can not by 'null'");
        }

        // Determine if we should rehash internal storage array.
        if (this.rehashNeeded()) {
            this.rehash();
        }

        // Calculate HashCode & internal Index
        int hashCode = key.hashCode();
        int index = this.getIndex(hashCode);

        // Debugging checks
        System.out.println("Hashcode: " + hashCode);
        System.out.println("First index: " + index);

        // Probe Chaining -> Check internal 'Placeholder' value.
        for (int i = 0; i < storage.length; i++) {
            int probeIndex = (index + i) % this.capacity;
            Entry<K, V> entry =  this.storage[probeIndex];

            // If 'Occupied' => Check if duplicate value or continue.
            if (entry.getStatus() == Placeholder.Occupied) {
                if (entry.getKey().equals(key)) {
                    V result = entry.getValue();
                    entry.addKeyAndValue(key, value);
                    return result;
                } else {
                    continue;
                }
            } else {
                // Entry is empty or dead => We insert at this spot.
                entry.addKeyAndValue(key, value);
                this.occupiedList.add(probeIndex);
                size++;
                return null;
            }
        }

        // Unreachable Code -> Should never execute this;
        return null;
    }

    // Main Remove-method => Remove the Dictionary-value
    public V remove(K key) {
        // Throw error if the key parameter if 'null'.
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null");
        }

        // Calculate HashCode & internal Index
        int hashCode = key.hashCode();
        int index = this.getIndex(hashCode);

        // Perform Probe Chaining
        for (int i = 0; i < storage.length; i++) {
            int probeIndex = (index + i) % this.capacity;
            Entry<K, V> entry = this.storage[probeIndex];
            Placeholder status = entry.getStatus();

            // If 'Occupied' => Check if internal key matches.
            if (status == Placeholder.Occupied) {
                // If key matches, ew remove the related value from storage.
                if (entry.getKey().equals(key)) {
                    V result = entry.getValue();
                    entry.removeKeyAndValue();
                    return result;
                }
                // If 'Tombstone' => Simply continue to next step in Chain.
            } else if (status == Placeholder.Tombstone) {
                continue;
            } else {
                // If 'Empty' => Chain is broken and key-value pair doesn't exist.
                return null;
            }
        }

        // Unreachable Code -> Should never execute this;
        return null;
    }

    // Main get-method => Return value related to specified key.
    public V get(K key) {
        // If key == null => System throws error.
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null");
        }

        // Calculate HashCode & internal Index
        int hashCode = key.hashCode();
        int index = this.getIndex(hashCode);

        // Initiate Probe Chain => Search for related key-value pair
        for (int i = 0; i < storage.length; i++) {
            int probeIndex = (index + i) % this.capacity;
            Entry<K, V> entry = this.storage[probeIndex];
            Placeholder status = entry.getStatus();

            // if 'Occupied' => Check if keys match & return value.
            if (status == Placeholder.Occupied) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
                // If 'Tombstone' => Continue to next step in probe chain.
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
    private void rehash() {
        // Print-functionality for debugging
        System.out.println("Rehash commencing...");
        System.out.println("Starting size is " + this.storage.length);

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

        // Print-functionality for debugging
        System.out.println("Rehash completed!");
        System.out.println("New storage size is " + this.storage.length);
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

    // Internal helper-method for determining if the storage array is currently empty.
    public boolean isEmpty() {
        return this.size == 0;
    }
}
