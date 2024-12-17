import java.util.ArrayList;
import java.util.Collections;

class Table<K extends Comparable<K>, V> {
    ArrayList<Entry<K, V>> data;

    public Table() {
        data = new ArrayList<>();
    }

    static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void insert(K key, V value) {
        data.add(new Entry<>(key, value));
    }

    public V find(K key) {
        sort();
        int left = 0, right = data.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = key.compareTo(data.get(mid).key);
            if (cmp == 0) {
                return data.get(mid).value;
            } else if (cmp < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return null;
    }

    public void remove(K key) {
        data.removeIf(entry -> entry.key.equals(key));
    }

    public void sort() {
        data.sort((entry1, entry2) -> entry1.key.compareTo(entry2.key));
    }

    public void sequentialSearch(K key) {
        for (Entry<K, V> entry : data) {
            if (entry.key.equals(key)) {
                System.out.println(entry.value);
                return;
            }
        }
        System.out.println("Not found");
    }
}
