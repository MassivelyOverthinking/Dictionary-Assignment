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

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Placeholder getStatus() {
        return status;
    }

    public void setStatus(Placeholder status) {
        this.status = status;
    }

    public void addKeyAndValue(K key, V value) {
        this.key = key;
        this.value = value;
        this.status = Placeholder.Occupied;
    }
}
