public class Entry<K, V> {
    private K key;
    private V value;
    private Placeholder status;

    public Entry() {
        key = null;
        value = null;
        status = Placeholder.Empty;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Placeholder getStatus() {
        return status;
    }

    public void addKeyAndValue(K key, V value) {
        this.key = key;
        this.value = value;
        this.status = Placeholder.Occupied;
    }

    public void removeKeyAndValue() {
        this.key = null;
        this.value = null;
        this.status = Placeholder.Tombstone;
    }
}
