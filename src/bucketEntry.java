public class bucketEntry<K, V> {
    private K key;
    private V value;

    public bucketEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public bucketEntry() {
        this.key = null;
        this.value = null;
    }
}
